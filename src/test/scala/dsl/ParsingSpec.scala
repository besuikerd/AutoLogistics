package dsl

import com.besuikerd.autologistics.common.lib.dsl.AutoLogisticsParser
import com.besuikerd.autologistics.common.lib.dsl.parser.DSLPrettyPrinter
import com.besuikerd.autologistics.common.lib.dsl.vm.CodeGenerator
import org.scalatest.{Inside, FlatSpec, Assertions}
import scala.collection.JavaConversions._

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.CharSequenceReader

class ParsingSpec extends FlatSpec with Inside{
  implicit def string2CharSequenceReader(s:String):CharSequenceReader = new CharSequenceReader(s)
  def parsing[A](p:Parsers)(parser: p.Parser[A], input:p.Input)(f:A => Unit) = parser.apply(input) match{
      case success:p.Success[A] @unchecked => f(success.get)
      case p.NoSuccess(msg, _) => Assertions.fail(msg)
    }

  def assertParses(p:Parsers)(parser:p.Parser[_], input:p.Input): Unit = parsing(p)(parser, input){_ =>}

  it should "parse the following programs" in {
    val p = AutoLogisticsParser
    parsing(p)(p.parser,
      """
        |<Chest> >> [[log]]
        |
      """.stripMargin){ statements =>
      for(statement <- statements){
        println(DSLPrettyPrinter.prettify(statement))
      }
    }

  }
}
