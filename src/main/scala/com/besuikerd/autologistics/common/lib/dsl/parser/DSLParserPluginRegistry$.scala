package com.besuikerd.autologistics.common.lib.dsl.parser

import com.besuikerd.autologistics.common.lib.dsl._

trait DSLParserPluginRegistry extends PluggableParsers { this:DSLParser =>
  private val _operands:scala.collection.mutable.Buffer[this.type#Parser[Expression]]  = scala.collection.mutable.Buffer()
  private val _binaryOperators:scala.collection.mutable.Map[Int, scala.collection.mutable.Buffer[(String, (Expression, String, Expression) => Expression)]] = scala.collection.mutable.Map()
  private val _statements:scala.collection.mutable.Buffer[this.type#Parser[Statement]] = scala.collection.mutable.Buffer()

  def registerOperands(operands:this.type#Parser[Expression]*) = _operands ++= operands
  def registerBinaryOperators(precedence:Int, operators:(String, (Expression, String, Expression) => Expression)*):Unit = _binaryOperators.get(precedence) match{
    case Some(buffer) => buffer ++= operators
    case None => {
      _binaryOperators += (precedence -> scala.collection.mutable.Buffer(operators:_*))
    }
  }
  def registerStatements(statements:this.type#Parser[Statement]*) = _statements ++= statements

  abstract override def operands = super.operands ++ _operands
  abstract override def binaryOperators = super.binaryOperators ++ _binaryOperators.map{case (a,b) => a -> b.toSeq}
  abstract override def statements = super.statements ++ _statements
}
