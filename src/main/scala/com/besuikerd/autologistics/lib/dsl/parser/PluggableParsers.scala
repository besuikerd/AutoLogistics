package com.besuikerd.autologistics.lib.dsl.parser

import com.besuikerd.autologistics.lib.dsl._

trait PluggableParsers { this:DSLParser =>
  def statements:Seq[Parser[Statement]] = Seq()
  def operands:Seq[Parser[Expression]] = Seq()
  def binaryOperators:Map[Int, Seq[(String, (Expression, String, Expression) => Expression)]] = Map()
}