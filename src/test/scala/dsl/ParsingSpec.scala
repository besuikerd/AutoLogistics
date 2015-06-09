package dsl

import org.scalatest.Assertions

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.CharSequenceReader

trait ParsingSpec {
  implicit def string2CharSequenceReader(s:String):CharSequenceReader = new CharSequenceReader(s)
  def parsing[A](p:Parsers)(parser: p.Parser[A], input:p.Input)(f:A => Unit) = parser.apply(input) match{
      case success:p.Success[A] => f(success.get)
      case fail:p.Failure => Assertions.fail(fail.msg)
    }
}
