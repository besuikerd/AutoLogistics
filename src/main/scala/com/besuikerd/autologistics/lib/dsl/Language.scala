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

//Binary operations
sealed abstract class BinaryExpression(val e1:Expression, val operator:String, val e2:Expression) extends Expression
case class Add(left:Expression, op:String, right:Expression) extends BinaryExpression(left, op, right)
case class Sub(left:Expression, op:String, right:Expression) extends BinaryExpression(left, op, right)
case class Mul(left:Expression, op:String, right:Expression) extends BinaryExpression(left, op, right)
case class Div(left:Expression, op:String, right:Expression) extends BinaryExpression(left, op, right)
case class Mod(left:Expression, op:String, right:Expression) extends BinaryExpression(left, op, right)

case class GT(left:Expression, op:String, right:Expression) extends BinaryExpression(left, op, right)
case class GTE(left:Expression, op:String, right:Expression) extends BinaryExpression(left, op, right)
case class LT(left:Expression, op:String, right:Expression) extends BinaryExpression(left, op, right)
case class LTE(left:Expression, op:String, right:Expression) extends BinaryExpression(left, op, right)
case class EQ(left:Expression, op:String, right:Expression) extends BinaryExpression(left, op, right)
case class NEQ(left:Expression, op:String, right:Expression) extends BinaryExpression(left, op, right)

case class And(left:Expression, op:String, right:Expression) extends BinaryExpression(left, op, right)
case class Or(left:Expression, op:String, right:Expression) extends BinaryExpression(left, op, right)

//Numeric expressions
sealed abstract class Number extends Expression
case class RealNumberConstant(value:Double) extends Expression
case class NaturalNumberConstant(value:Int) extends Expression

case class StringLiteral(value:String) extends Expression
case class BooleanConstant(value:Boolean) extends Expression