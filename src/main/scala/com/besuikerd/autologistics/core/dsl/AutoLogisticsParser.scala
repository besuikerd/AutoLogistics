package com.besuikerd.autologistics.core.dsl

import com.besuikerd.autologistics.lib.dsl._
import com.besuikerd.autologistics.lib.dsl.parser._
import com.besuikerd.autologistics.lib.dsl.vm.NaturalNumber

object AutoLogisticsParser extends DSLParser
  with AutoLogisticsParserExtensions

trait AutoLogisticsParserExtensions extends PluggableParsers
  with ParserImplicits
{ this: DSLParser =>

  lazy val itemRef:Parser[Expression] = ("<" ~> expression <~ ":") ~ expression ~ ((":" ~> expression).? <~ ">") ^^ {
    case mod ~ name ~ meta => Application("_getItem", List(mod, name, meta.getOrElse(NaturalNumberConstant(-1))))
  }

  lazy val filtered:Parser[Expression] = referrable ~ ("@" ~> expression) ^^ {
    case e ~ filter => Application("_itemFilter", List(e, filter))
  }

  lazy val coordinate = "(" ~> expression ~ ("," ~> expression) ~ ("," ~> expression)  <~ ")"

  lazy val relativeCoordinate = "~" ~> coordinate ^^ {
    case x ~ y ~ z => new ObjectExpression(Map(
      "type" -> StringLiteral("relative"),
      "x" -> x,
      "y" -> y,
      "z" -> z
    ))
  }

  lazy val absCoordinate:Parser[Expression] = coordinate ^^ {
    case x ~ y ~ z => new ObjectExpression(Map(
      "type" -> StringLiteral("absolute"),
      "x" -> x,
      "y" -> y,
      "z" -> z
    ))
  }

  override def operands:Seq[Parser[Expression]] = Seq(relativeCoordinate, absCoordinate, itemRef, filtered) ++ super.operands
  override def binaryOperators:Map[Int, Seq[(String, (Expression, String, Expression) => Expression)]] = super.binaryOperators ++ Map(
    10 -> Seq((">>", Application.apply("_transferTo") _))
  )
}
