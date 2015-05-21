package dsl

import com.besuikerd.autologistics.lib
import com.besuikerd.autologistics.lib.dsl._
import com.besuikerd.autologistics.lib.dsl.parser.{DSLPrettyPrinter, DSLParser, DSLParserPluginRegistry}
import com.besuikerd.autologistics.lib.dsl.vm.CodeGenerator
import com.besuikerd.autologistics.lib.dsl.vm._
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
      registerBinaryOperators(100, ("->", Application.apply("moveTo")))
      registerBinaryOperators(101, ("times", Application.apply))
      registerBinaryOperators(8, ("to", Application.apply("mkList")))
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
        |add = \x y -> x + y
        |g = \f a b -> f(a,b)
        |println(g(add, 2, 3))
        |""".stripMargin

    parsing(parser)(parser.parser, program){ statements =>
      statements.foreach{s => println(DSLPrettyPrinter.prettify(s))}
      val instructions = CodeGenerator.generate(statements)
      instructions.foreach{
        case PushClosure(optName, bindings, free, body) =>
          println(s"PushClosure($optName, $bindings, $free")
          body.foreach(x => println("\t" + x))
          println(")")
        case other => println(other)
      }
      val vm = new VirtualMachine()
      vm.load(instructions)
      vm.addNativePartial("print"){args => args.foreach(a => print(a.stringRepresentation)); NilValue}
      vm.addNativePartial("println"){args => args.foreach(a => println(a.stringRepresentation)); NilValue}

      vm.run(1000)
      println("scopes: " + vm.scopes)
      println("instructions: " + vm.instructions)
      println("stack: " + vm.stack)
    }
  }
}
