package dsl

import com.besuikerd.autologistics.lib.dsl._
import com.besuikerd.autologistics.lib.dsl.parser.{DSLPrettyPrinter, DSLParser, DSLParserPluginRegistry}
import org.scalatest.{FlatSpec, Inside, Matchers}
import com.besuikerd.autologistics.lib.dsl.parser._

class DSLSpec extends FlatSpec
with Matchers
with Inside
with ParsingSpec
{
  "DSL Parser" should "parse a simple program" in {
    object parser extends DSLParser with DSLParserPluginRegistry with ParserImplicits{
      lazy val itemRef:Parser[Expression] = "<" ~> ident ~ ":" ~ ident <~ ">" ^^ {
        case s => Application(VariableExpression("_getItem"), List(StringLiteral(concat(s))))
      }

      registerOperands(itemRef)
//      registerBinaryOperators(100, "->")
//      registerBinaryOperators(101, "times")
      registerBinaryOperators(8, ("to", (e1, op, e2) => Application(VariableExpression("mkList"), List(e1, e2))))
    }

//    val program =
//      """
//        |inv1 -> inv2 times 64
//        |print(1 to 100)
//        |(\x -> {
//        | return 42
//        |})("hoi")
//        |inv1 = "das"
//        |inv2 = "bla"
//        |inv1 * inv2 * x
//      """.stripMargin

    val program =
      """
        |
        |print(42 + 3 * 4)
        |x = 1 to 42
        |
        |""".stripMargin

    parsing(parser)(parser.parser, program){ statements =>
      statements.foreach{s => println(DSLPrettyPrinter.prettify(s))}
    }
  }
}
