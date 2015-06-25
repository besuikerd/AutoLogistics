package com.besuikerd.autologistics.common.lib.dsl.parser
import scala.util.matching.Regex
import scala.util.parsing.combinator.{Parsers, ImplicitConversions, JavaTokenParsers}
import com.besuikerd.autologistics.common.lib.dsl._
import com.besuikerd.autologistics.common.lib.extensions.IntStringExtensions

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

  lazy val sortedBinaryOperators:List[Parser[(String, (Expression, String, Expression) => Expression)]] = binaryOperators.mapValues(_.map{case (op, f) => literal(op).map((_, f))}.reduceRight(_ | _)).toList.sortBy(-_._1).map(_._2) //reverse sorted to build up parser from the bottom

  lazy val binExp:Parser[Expression] = {

    /*def chain(list: List[Parser[(String, (Expression, String, Expression) => Expression)]]): Parser[Expression] = {

      list match {
        case x :: Nil => {
          def next: Parser[Expression] = operand ~ (x ~ next).* ^^ {
            case exp ~ List() => exp
            case exp ~ xs => xs.foldLeft(exp) {
              case (acc, op ~ exp) => {
                println(exp)
                op._2(acc, op._1, exp)
              }
            }
          }
          next
        }
        case x :: xs => {
          def next: Parser[Expression] = chain(xs) ~ (x ~ next).* ^^ {
            case exp ~ List() => exp
            case exp ~ xs => xs.foldLeft(exp) {
              case (acc, op ~ exp) => {
                println(exp)
                op._2(acc, op._1, exp)
              }
            }
          }
          next
        }

      }
    }
    chain(sortedBinaryOperators)
  }*/


      sortedBinaryOperators.foldRight(operand){case (cur, acc) =>
      def next: Parser[Expression] = (acc ~ (cur ~ acc).*) ^^ {
        case exp ~ List() => exp
        case exp ~ xs => xs.foldLeft(exp) {
          case (acc, op ~ exp) => {
            op._2(acc, op._1, exp)
          } //Application(VariableExpression(op), List(acc, exp))
        }
      }
      next
    }
  }


  lazy val statement: Parser[Statement] = statements.reduceRight(_ | _) <~ ";".?
  lazy val operand:Parser[Expression] = operands.reduceRight(_ | _)
}

trait DSLStatements extends PluggableParsers with ImplicitConversions{this:DSLParser =>
  lazy val assignment = (ident <~ "=") ~ expression
  lazy val assignmentStatement = assignment ^^ {case variable ~ exp => Assignment(variable, exp, false)}
  lazy val assignmentStatementLocal = "local" ~> assignment ^^ {case variable ~ exp => Assignment(variable, exp, true)}
  lazy val expressionStatement: Parser[Statement] = expression.map(ExpressionStatement)
  lazy val assignField = referrable ~ ("." ~> ident).+ ~ ("=" ~> expression) ^^ AssignField
  lazy val assignIndex = referrable ~ ("[" ~> expression <~ "]").+ ~ ("=" ~> expression) ^^ AssignIndex
  lazy val whileStatement = ("while" ~> "(" ~> expression <~ ")") ~ (blockExp ^^ ExpressionStatement | statement) ^^ WhileStatement

  override def statements: Seq[Parser[Statement]] = super.statements ++ Seq(
    assignIndex,
    assignField,
    assignmentStatementLocal,
    assignmentStatement,
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

  lazy val indexExp:Parser[IndexExpression] = (objectField | referrable) ~ ("[" ~> expression <~ "]").+ ^^ IndexExpression

  lazy val objectField:Parser[ObjectFieldExpression] = referrable ~ ("." ~> ident).+ ^^ ObjectFieldExpression

  lazy val listExp:Parser[ListExpression] = "[" ~> newline.* ~> repsep(expression, ",".? ~ newline.*) <~ "]" <~ newline.* ^^ ListExpression

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
    indexExp |
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