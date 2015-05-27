package com.besuikerd.autologistics.core.dsl

import com.besuikerd.autologistics.lib.dsl._
import com.besuikerd.autologistics.lib.dsl.parser._

object AutoLogisticsParser extends DSLParser
  with AutoLogisticsParserExtensions

trait AutoLogisticsParserExtensions extends PluggableParsers
  with ParserImplicits
{ this: DSLParser =>

  lazy val itemRef:Parser[Expression] = "<" ~> ident ~ ":" ~ ident <~ ">" ^^ {
    case s => Application("_getItem", List(StringLiteral(concat(s))))
  }

  lazy val filtered:Parser[Expression] = referrable ~ ("[" ~> repsepSplit((ident <~ "=") ~ expression, expression, ",")  <~ "]") ^^ {
    case expr ~ ((a,b)) => {
      val as = a.map{case a~b => (a,b)}.toMap
      Application("_filter", expr :: ObjectExpression(as) :: b)
    }
  }


  override def operands:Seq[Parser[Expression]] = Seq(itemRef, filtered) ++ super.operands
  override def binaryOperators:Map[Int, Seq[(String, (Expression, String, Expression) => Expression)]] = super.binaryOperators ++ Map(
    10 -> Seq((">>", Application.apply("_transferTo") _))
  )
}
