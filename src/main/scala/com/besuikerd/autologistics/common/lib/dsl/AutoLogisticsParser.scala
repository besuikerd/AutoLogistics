package com.besuikerd.autologistics.common.lib.dsl

import com.besuikerd.autologistics.common.lib.dsl.parser._

object AutoLogisticsParser extends DSLParser
  with AutoLogisticsParserExtensions

trait AutoLogisticsParserExtensions extends PluggableParsers
  with ParserImplicits
{ this: DSLParser =>

  lazy val itemRef:Parser[Expression] = ("<" ~> ident <~ ":") ~ ident ~ ((":" ~> naturalNumber).? <~ ">") ^^ {
    case mod ~ name ~ meta => {
      val mapping = Map[String, Expression](
        "type" -> StringLiteral("item"),
        "mod" -> StringLiteral(mod),
        "name" -> StringLiteral(name)
      )
      ObjectExpression(meta.map(x => mapping + ("meta" -> x)).getOrElse(mapping))
    }
  }

  lazy val filtered:Parser[Expression] = referrable ~ ("@" ~> (listExp | referrable)) ^^ {
    case e ~ filter => {
      Application("_itemFilter", List(e, filter))
    }
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
