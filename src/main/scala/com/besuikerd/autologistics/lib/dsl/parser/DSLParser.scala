package com.besuikerd.autologistics.lib.dsl.parser

import com.besuikerd.autologistics.lib.dsl
import com.besuikerd.autologistics.lib.dsl.vm.Branch

import scala.util.matching.Regex
import scala.util.parsing.combinator.{Parsers, ImplicitConversions, JavaTokenParsers}
import com.besuikerd.autologistics.lib.dsl._
import com.besuikerd.autologistics.lib.extensions.IntStringExtensions

trait DSLParser extends JavaTokenParsers
  with ImplicitConversions
  with DSLBinaryExpressions
  with DSLOperands
  with DSLStatements
  with PluggableParsers
{

  override protected val whiteSpace: Regex = """[^\S\n]+""".r
  lazy val newline:Parser[String] = literal("\n")

  lazy val EOF = (".".r.map(Some(_)) | success(None)).flatMap{
    case Some(s) => failure(s"Expected: EOF, got: $s")
    case None => success(())
  }

  lazy val parser:Parser[List[Statement]] = newline.* ~> (statement <~ (newline.* ~ ";".?)).* <~ EOF

  lazy val integer:Parser[Int] = wholeNumber.flatMap{n =>
    try{
      success(n.toInt)
    } catch{
      case e:NumberFormatException => failure(s"could not convert $n to an integer")
    }
  }

  lazy val expression:Parser[Expression] = binExp <~ newline.?

  lazy val sortedBinaryOperators:Seq[Parser[(String, (Expression, String, Expression) => Expression)]] = binaryOperators.mapValues(_.map{case (op, f) => literal(op).map((_, f))}.reduceRight(_ | _)).toSeq.sortBy(-_._1).map(_._2) //reverse sorted to build up parser from the bottom
  lazy val binExp = {
    sortedBinaryOperators.foldRight(operand){ (cur, acc) =>
      def next: Parser[Expression] = (acc ~ (cur ~ next).*) ^^ {
        case exp ~ List() => exp
        case exp ~ xs => xs.foldRight(exp) {
          case (op ~ exp, acc) => op._2(acc, op._1, exp) //Application(VariableExpression(op), List(acc, exp))
        }
      }
      next
    }
  }

  lazy val statement: Parser[Statement] = statements.reduceRight(_ | _) <~ ";".?
  lazy val operand:Parser[Expression] = operands.reduceRight(_ | _)
}

trait DSLStatements extends PluggableParsers with ImplicitConversions{this:DSLParser =>
  lazy val assignment = (ident <~ "=") ~ expression ^^ {case variable ~ exp => Assignment(variable, exp)}
  lazy val expressionStatement: Parser[Statement] = expression.map(ExpressionStatement)
  lazy val assignField = referrable ~ ("." ~> ident).+ ~ ("=" ~> expression) ^^ AssignField
  lazy val assignIndex = referrable ~ ("[" ~> expression <~ "]").+ ~ ("=" ~> expression) ^^ AssignIndex
  lazy val whileStatement = ("while" ~> "(" ~> expression <~ ")") ~ statement ^^ WhileStatement

  override def statements: Seq[Parser[Statement]] = super.statements ++ Seq(
    assignIndex,
    assignField,
    assignment,
    whileStatement,
    expressionStatement
  )
}

trait DSLBinaryExpressions extends PluggableParsers{ this:DSLParser =>



  abstract override def binaryOperators:Map[Int, Seq[(String, (Expression, String, Expression) => Expression)]] = super.binaryOperators ++ Map(
    1 -> Seq(("*", Mul), ("/", Div), ("%", Mod)),
    2 -> Seq(("+", Add), ("-", Sub)),
    3 -> Seq((">", GT), ("<", LT), (">=", GTE), ("<=", LTE), ("==", EQ), ("!=", NEQ)),
    4 -> Seq(("&&", And)),
    5 -> Seq(("||", Or))
  )
}

trait DSLOperands extends PluggableParsers { this:DSLParser =>

  lazy val variable:Parser[VariableExpression] = ident ^^ VariableExpression.apply

  lazy val realNumber:Parser[RealNumberConstant] = floatingPointNumber ^^ (x => RealNumberConstant(x.toDouble))
  lazy val naturalNumber:Parser[NaturalNumberConstant] = integer ^^ NaturalNumberConstant
  //parses either natural or real
  lazy val number:Parser[Expression] = floatingPointNumber ^^ {case n => n.optToInt.map(NaturalNumberConstant).getOrElse(RealNumberConstant(n.toDouble))}


  lazy val bool:Parser[BooleanConstant] = ("true" | "false") ^^ {case s => BooleanConstant(s.equals("true"))}

  lazy val string:Parser[StringLiteral] = stringLiteral ^^ {case s => StringLiteral(s.substring(1, s.length - 1))}

  lazy val parensExp = "(" ~> expression <~ ")"

  lazy val lambda = ("\\" ~> ident.*) ~ ("->" ~> expression) ^^ {
    case bindings ~ exp => LambdaExpression(bindings, exp)
  }

  lazy val application:Parser[Expression] = applyable ~ ( "(" ~> repsep(expression, ",") <~  ")" ).* ^^ {
    case exp ~ argumentLists => argumentLists.foldLeft(exp)((acc, cur) => Application(acc, cur))
  }

  lazy val objectExp:Parser[ObjectExpression] = "{" ~> newline.* ~> repsep((ident <~ newline.* <~ "=" <~ newline.*) ~ expression, ",".? ~ newline.*) <~ newline.* <~ "}" ^^ {_.map {case id ~ expr => (id, expr)}.toMap} ^^ ObjectExpression

  lazy val indexExp:Parser[IndexExpression] = referrable ~ ("[" ~> expression <~ "]").+ ^^ IndexExpression

  lazy val objectField:Parser[ObjectFieldExpression] = referrable ~ ("." ~> ident).+ ^^ ObjectFieldExpression

  lazy val listExp:Parser[ListExpression] = "[" ~> newline.* ~> repsep(expression, "," ~ newline.*) <~ "]" <~ newline.* ^^ ListExpression

  lazy val ifElse:Parser[IfElseExpression] = ("if" ~> "(" ~> expression <~ ")") ~ expression ~ ("else" ~> expression).? ^^ IfElseExpression

  lazy val blockExp:Parser[Expression] = "{" ~> newline.* ~> repsep(statement, newline.*) <~ newline.* <~ "}" ^^ BlockExpression

  lazy val nullExp:Parser[Expression] = "null" ^^ (_ => NullExpression)

  lazy val referrable: Parser[Expression] =
    parensExp |
//    blockExp |
    variable

  //TODO something like this: operands.fi.lter(!_.equals(app)).reduceRight(_ | _)
  lazy val applyable: Parser[Expression] =
    lambda |
    objectField |
    referrable

  abstract override def operands = super.operands ++ Seq(
    bool |
    nullExp |
    ifElse |
    application |
    objectField |
    number |
    string |
    lambda |
    parensExp |
    listExp |
    objectExp |
    blockExp |
    variable
  )
}