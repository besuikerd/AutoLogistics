package dsl

import com.besuikerd.autologistics.core.dsl.AutoLogisticsParser
import com.besuikerd.autologistics.lib
import com.besuikerd.autologistics.lib.dsl._
import com.besuikerd.autologistics.lib.dsl.parser.{DSLPrettyPrinter, DSLParser, DSLParserPluginRegistry}
import com.besuikerd.autologistics.lib.dsl.vm.CodeGenerator
import com.besuikerd.autologistics.lib.dsl.vm._
import com.besuikerd.autologistics.tile.TileEntityMod
import com.besuikerd.autologistics.tile.traits.{TileCable, TileLogistic, TileVirtualMachine}
import org.scalatest.{FlatSpec, Inside, Matchers}
import com.besuikerd.autologistics.lib.dsl.parser._
import scala.collection.mutable.{Map => MMap}

class DSLSpec extends FlatSpec
with Matchers
with Inside
with ParsingSpec
{
  "DSL Parser" should "parse a simple program" in {
    object parser extends DSLParser with DSLParserPluginRegistry with ParserImplicits{
      lazy val itemRef:Parser[Expression] = ("<" ~> ident <~ ":") ~ (ident <~ ">") ^^ {
        case mod ~ name => Application(VariableExpression("_getItem"), List(StringLiteral(mod), StringLiteral(name)))
      }

      registerOperands(itemRef)
//      registerBinaryOperators(100, ("->", Application.apply("moveTo")))
//      registerBinaryOperators(101, ("times", Application.apply))
//      registerBinaryOperators(8, ("to", Application.apply("mkList")))
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
        |x = {
        | bla = {
        |   list = [1,2,3]
        | }
        |}
        |y = x.bla.list[1]
        |println(y)
      """.stripMargin




    /*

    coal = <minecraft:coal>
    chest = <minecraft:chest>
    furnace = <minecraft:furnace>

    refuel = \ ->

    while(true){
      chest >> furnace[north, coal, amount = 5]
      chest[<minecraft:wood>, 1]
      furnace[<minecraft:charcoal>] >> chest
    }

     */

    println("parsing time: " + timed{parsing(parser)(parser.parser, program){x => }})

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

      println("========================")

      val vm = new VirtualMachine()
      vm.load(instructions)
      vm.addNativePartial("print"){args => args.foreach(a => print(a.stringRepresentation)); NilValue}
      vm.addNativePartial("println"){args => args.foreach(a => println(a.stringRepresentation)); NilValue}

      val time = timed{val cycles = vm.run(10000); println(s"executed $cycles instructions")}
      println(s"time taken: " + time)
      println("scopes: " + vm.scopes)
      println("instructions: " + vm.instructions)
      println("stack: " + vm.stack)
    }
  }


  "AutoLogisticsParser" should "parse additional operands and operators" in {
//    val program =
//      """
//coal = <minecraft:charcoal>
//chest = <minecraft:chest>
//tchest = <minecraft:trapped_chest>
//furnace = <minecraft:furnace>
//
//coord = (1,-2,3)
//
//chest >> furnace[north, coal, amount = 5]
//chest[<minecraft:wood>, 1] >> furnace
//furnace[<minecraft:charcoal>] >> chest
//
//
//      """.stripMargin

    val program =
      """
        |x = ~(1,2,3)
        |filtered = x@[north]
        |println(filtered)
      """.stripMargin

    parsing(AutoLogisticsParser)(AutoLogisticsParser.parser, program){ statements =>
      object tile extends TileEntityMod with TileVirtualMachine with TileCable with TileLogistic
      val vm = tile.virtualMachine

      statements foreach println


      val code = CodeGenerator.generate(statements)
      vm.load(code)

      vm.run(10000)


      println("scopes: " + vm.scopes)
      println("instructions: " + vm.instructions)
      println("stack: " + vm.stack)
    }
  }

  def timed(f: => Unit): Long =  {
    val t0 = System.currentTimeMillis()
    f
    System.currentTimeMillis() - t0
  }

  it should "successfully parse the following expressions" in {
    val program =
      """
         |item
      """.stripMargin

    parsing(AutoLogisticsParser)(AutoLogisticsParser.parser, program){ p =>
      println(p)
      CodeGenerator.generate(p).foreach(println)
    }
  }

}
