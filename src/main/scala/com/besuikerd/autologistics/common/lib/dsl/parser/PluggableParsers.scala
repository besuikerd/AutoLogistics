package com.besuikerd.autologistics.common.lib.dsl.parser

import com.besuikerd.autologistics.common.lib.dsl._

trait PluggableParsers { this:DSLParser =>
  def statements:Seq[Parser[Statement]] = Seq()

  /**
   * parsed before binary expressions
   */
  def expressions: Seq[Parser[Expression]] = Seq()

  /**
   * parsed after binary expressions
   */
  def operands:Seq[Parser[Expression]] = Seq()

  def binaryOperators:Map[Int, Seq[(String, (Expression, String, Expression) => Expression)]] = Map()
}