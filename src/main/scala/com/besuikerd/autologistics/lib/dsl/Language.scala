package com.besuikerd.autologistics.lib.dsl

import scala.tools.nsc.doc.model.ImplicitConversion
import scala.util.parsing.combinator.JavaTokenParsers

object Language{
}

sealed abstract class ASTNode

//Statements
sealed abstract class Statement extends ASTNode

case class Assignment(variable:String, binding:Expression) extends Statement
case class ExpressionStatement(expression:Expression) extends Statement
case class ReturnStatement(value:Expression) extends Statement

//Expressions
sealed abstract class Expression extends ASTNode

case class VariableExpression(name:String) extends Expression
case class Application(expression:Expression, arguments:List[Expression]) extends Expression

case class BlockExpression(statements:List[Statement]) extends Expression
case class LambdaExpression(bindings:List[String], body:Expression) extends Expression

//Numeric expressions
sealed abstract class Number extends Expression
case class RealNumberConstant(value:Double) extends Expression
case class NaturalNumberConstant(value:Int) extends Expression

case class StringLiteral(value:String) extends Expression
case class BooleanConstant(value:Boolean) extends Expression