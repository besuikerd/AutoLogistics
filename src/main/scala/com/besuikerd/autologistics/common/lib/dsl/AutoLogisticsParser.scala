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

//  lazy val filtered:Parser[Expression] = referrable ~ ("@" ~> (listExp | referrable)) ^^ {
//    case e ~ filter => {
//      Application("_filter", List(e, filter))
//    }
//  }


  lazy val stringCharacters:Parser[String] = """([^<>\p{Cntrl}\\]|\\[\\'<>bfnrt]|\\u[a-fA-F0-9]{4})*+""".r
  lazy val itemName:Parser[Expression] = "<" ~> stringCharacters <~ ">"^^ {
    case name => ObjectExpression(Map(
      "type" -> StringLiteral("name"),
      "name" -> StringLiteral(name)
    ))
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

  lazy val itemType: Parser[Expression] = itemRef | itemName | referrable

  lazy val transferTo: Parser[Expression] = repNsep(2, itemFilter | itemType | listExp, ">>") ^^ {
    case items => items.tail.foldLeft(items.head){(acc, cur) => Application(acc, "_transfer", cur)
    }
  }

  lazy val itemFilter: Parser[Expression] = (itemType <~ "@") ~ (listExp | operand | beforeExpressions) ^^ {
    case item ~ (filter@ListExpression(_)) => Application(item, "_filter", filter)
    case item ~ filter => Application(item, "_filter", ListExpression(List(filter)))
  }

  override def expressions: Seq[Parser[Expression]] = Seq(transferTo | itemFilter | itemRef | itemName) ++ super.expressions

  override def operands:Seq[Parser[Expression]] = Seq(relativeCoordinate, absCoordinate) ++ super.operands
//  override def binaryOperators:Map[Int, Seq[(String, (Expression, String, Expression) => Expression)]] = super.binaryOperators ++ Map(
//  )
}
