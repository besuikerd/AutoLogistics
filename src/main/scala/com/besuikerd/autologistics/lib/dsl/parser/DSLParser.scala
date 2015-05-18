package com.besuikerd.autologistics.lib.dsl.parser

import scala.util.matching.Regex
import scala.util.parsing.combinator.{Parsers, ImplicitConversions, JavaTokenParsers}
import com.besuikerd.autologistics.lib.dsl._
import com.besuikerd.autologistics.lib.util.int._

trait DSLParser extends JavaTokenParsers
  with ImplicitConversions
  with DSLBinaryExpressions
  with DSLOperands
  with DSLStatements
  with PluggableParsers
  //with DSLParserPluginRegistry
{

  override protected val whiteSpace: Regex = """[^\S\n]+""".r
  lazy val newline:Parser[String] = """\n|\r\n""".r

  lazy val EOF = (".".r.map(Some(_)) | success(None)).flatMap{
    case Some(s) => failure(s"Expected: EOF, got: $s")
    case None => success(())
  }

  lazy val parser:Parser[List[Statement]] = newline.* ~> (statement <~ (newline.* ~ ";".?)).* <~ EOF

  lazy val expression:Parser[Expression] = binExp <~ newline.?

  lazy val sortedBinaryOperators:Seq[Parser[String]] = binaryOperators.mapValues(_.map(literal).reduceRight(_ | _)).toSeq.sortBy(-_._1).map(_._2) //reverse sorted to build up parser from the bottom
  lazy val binExp = {
    sortedBinaryOperators.foldRight(operand){ (cur, acc) =>
      def next: Parser[Expression] = (acc ~ (cur ~ next).*) ^^ {
        case exp ~ List() => exp
        case exp ~ xs => xs.foldRight(exp) {
          case (op ~ exp, acc) => Application(VariableExpression(op), List(acc, exp))
        }
      }
      next
    }
  }

  lazy val statement: Parser[Statement] = statements.reduceRight(_ | _)
  lazy val operand:Parser[Expression] = operands.reduceRight(_ | _)
}

trait DSLStatements extends PluggableParsers with ImplicitConversions{this:DSLParser =>
  lazy val returnStatement: Parser[Statement] = "return" ~> expression ^^ {case e => ReturnStatement(e)}
  lazy val assignment = (ident <~ "=") ~ expression ^^ {case variable ~ exp => Assignment(variable, exp)}
  lazy val expressionStatement: Parser[Statement] = expression.map(ExpressionStatement)

  override def statements: Seq[Parser[Statement]] = super.statements ++ Seq(
    returnStatement,
    assignment,
    expressionStatement
  )
}

trait DSLBinaryExpressions extends PluggableParsers{ this:DSLParser =>
  abstract override def binaryOperators:Map[Int, Seq[String]] = super.binaryOperators ++ Map(
    1 -> Seq("*", "/", "%"),
    2 -> Seq("+", "-"),
    3 -> Seq(">", "<", ">=", "<=", "==", "!="),
    4 -> Seq("&&"),
    5 -> Seq("||")
  )
}

trait DSLOperands extends PluggableParsers { this:DSLParser =>

  lazy val variable:Parser[VariableExpression] = ident ^^ VariableExpression.apply

  lazy val realNumber:Parser[RealNumberConstant] = floatingPointNumber ^^ (x => RealNumberConstant(x.toDouble))
  lazy val naturalNumber:Parser[NaturalNumberConstant] = wholeNumber ^^ {x => NaturalNumberConstant(x.toInt)}
  //parses either natural or real
  lazy val number:Parser[Expression] = floatingPointNumber ^^ {case n => n.optToInt.map(NaturalNumberConstant).getOrElse(RealNumberConstant(n.toDouble))}



  lazy val string:Parser[StringLiteral] = stringLiteral ^^ {case s => StringLiteral(s.substring(1, s.length - 1))}

  lazy val parensExp = "(" ~> expression <~ ")"

  lazy val lambda = ("\\" ~> ident.*) ~ ("->" ~> expression) ^^ {
    case bindings ~ exp => LambdaExpression(bindings, exp)
  }

  lazy val application:Parser[Expression] = applyable ~ ( "(" ~> repsep(expression, ",") <~  ")" ).* ^^ {
    case exp ~ argumentLists => argumentLists.foldRight(exp)((cur, acc) => Application(acc, cur))
  }

  lazy val blockExp:Parser[Expression] = "{" ~> (statement.*) <~ "}" ^^ BlockExpression

  //TODO something like this: operands.fi.lter(!_.equals(app)).reduceRight(_ | _)
  lazy val applyable: Parser[Expression] =
    lambda |
    parensExp |
    blockExp |
    variable


  abstract override def operands = super.operands ++ Seq(
    application |
    number |
    string |
    lambda |
    parensExp |
    blockExp |
    variable
  )
}