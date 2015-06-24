package com.besuikerd.autologistics.common.lib.dsl

import com.besuikerd.autologistics.common.lib.dsl.vm.instruction.Instruction

object Language{
}

sealed abstract class ASTNode

//Statements
sealed abstract class Statement extends ASTNode

case class Assignment(variable:String, binding:Expression, isLocal:Boolean) extends Statement
case class AssignField(obj:Expression, fields:List[String], binding:Expression) extends Statement
case class AssignIndex(obj:Expression, indexes:List[Expression], binding:Expression) extends Statement
case class ExpressionStatement(expression:Expression) extends Statement
case class WhileStatement(condition:Expression, body:Statement) extends Statement

//Expressions
sealed abstract class Expression extends ASTNode

case class VariableExpression(name:String) extends Expression
case class ObjectFieldExpression(objExpression:Expression, fields:List[String]) extends Expression
case class IndexExpression(expression:Expression, indexes:List[Expression]) extends Expression

case class Application(expression:Expression, arguments:List[Expression]) extends Expression
object Application{
  def apply(e1:Expression, name:String, e2:Expression):Application = Application(VariableExpression(name), List(e1, e2))
  def apply(name:String)(e1:Expression, op:String, e2:Expression):Application = apply(e1, name, e2)
  def apply(name:String, arguments:List[Expression]):Application = Application(VariableExpression(name), arguments)
}

case class BlockExpression(statements:List[Statement]) extends Expression
case class LambdaExpression(bindings:List[String], body:Expression) extends Expression

case class IfElseExpression(condition:Expression, ifExpression: Expression, elseExpression:Option[Expression]) extends Expression

case class ObjectExpression(fields:Map[String, Expression]) extends Expression

case class ListExpression(values:List[Expression]) extends Expression

//case class Instructions(instructions:List[Instruction]) extends Expression

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

case object NullExpression extends Expression
case class StringLiteral(value:String) extends Expression
case class BooleanConstant(value:Boolean) extends Expression