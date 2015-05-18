package com.besuikerd.autologistics.lib.dsl.vm

import org.scalatest.fixture

import scala.collection.mutable.Stack
import scala.collection.mutable.Map
import scala.reflect.runtime.universe._

class VirtualMachine {
  type Scope = Map[String, StackValue]

  val stack = Stack[StackValue]()
  val globalScope:Scope = Map[String, StackValue]()
  val scopes = Stack[Scope](globalScope)
  val instructions = Stack[Instruction]()


  /*
  def popTyped[A <: StackValue : TypeTag](f:A => Unit): Unit ={
    val tpe = typeTag[A].tpe
    if(tpe <:<  typestack.top){
      f(stack.pop().asInstanceOf[A])
    } else{
      crash(s"expected type: ${typeTag[A].tpe}, got: ${stack.top}")
    }
  }

  def poptionalTyped[A <: StackValue: TypeTag]:Option[A] = {
    if(stack.top.isInstanceOf[A]){
      Some(stack.pop().asInstanceOf[A])
    } else{
      None
    }
  }
  */

  def pop() = stack.pop()
  def push(value:StackValue) = stack.push(value)

  def crash(msg:String) = instructions.push(Crash(msg))
}

trait Instruction{
  def execute(machine: VirtualMachine):Unit
}

sealed abstract class DefaultInstruction(val f:VirtualMachine => Unit) extends Instruction{
  override def execute(machine: VirtualMachine): Unit = f(machine)
}


object Pop extends DefaultInstruction(_.stack.pop())


object OpenScope extends DefaultInstruction(_.scopes.push(Map()))
object CloseScope extends DefaultInstruction(_.scopes.pop())
case class Crash(msg:String) extends DefaultInstruction(_.crash(msg))

case class Push(value:StackValue) extends DefaultInstruction(_.stack.push(value))

case class Get(s:String) extends DefaultInstruction(machine =>{
  machine.scopes.map(_.get(s)).find(_.isDefined) match{
    case Some(Some(value)) => machine.stack.push(value)
    case Some(None) => machine.crash(s"Value not found: $s")
    case None => machine.crash(s"Value not found: $s")
  }
})

case class Put(s:String) extends DefaultInstruction(machine => {
  machine.scopes.top.put(s, machine.pop())
})

sealed abstract class NumericInstruction(f: (Double, Double) => Double) extends DefaultInstruction(machine =>
  machine.pop match {
    case RealNumber(m) => machine.pop match {
      case n:NumericStackValue => machine.push(RealNumber(f(m, n.value)))
      case StringValue(s) => machine.push(StringValue(m.toString + s))
      case other => machine.crash(s"can not add ${other.getClass.getSimpleName}")
    }
    case NaturalNumber(m) => machine.pop match{
      case NaturalNumber(n) => machine.push(NaturalNumber(f(m,n).toInt))
      case n:NumericStackValue => machine.push(RealNumber(f(m, n.value)))
      case StringValue(s) => machine.push(StringValue(m.toString + s))
      case other => machine.crash(s"can not add ${other.getClass.getSimpleName}")
    }
    case StringValue(s) => machine.push(StringValue(s + machine.pop().stringRepresentation))
    case other => machine.crash(s"can not apply ${getClass.getSimpleName} to $other")
  }
)

object AddInstruction extends NumericInstruction(_ + _)
object SubInstruction extends NumericInstruction(_ - _)
object MulInstruction extends NumericInstruction(_ * _)
object DivInstruction extends NumericInstruction(_ / _)
object ModInstruction extends NumericInstruction(_ % _)

sealed abstract class CompareInstruction(f: (Double, Double) => Boolean) extends DefaultInstruction(machine =>
  machine.pop match {
    case m:NumericStackValue => machine.pop match{
      case n:NumericStackValue => machine.push(BooleanValue(f(m.value, n.value)))
    }
    case other => machine.crash(s"can not apply ${getClass.getSimpleName} to ${other.getClass.getSimpleName}")
  }
)

object EQInstruction extends CompareInstruction(_ == _)
object NEQInstruction extends CompareInstruction(_ != _)
object GTInstruction extends CompareInstruction(_ > _)
object GTEInstruction extends CompareInstruction(_ >= _)
object LTInstruction extends CompareInstruction(_ < _)
object LTEInstruction extends CompareInstruction(_ <=  _)

object Instruction{
  def apply(f:VirtualMachine => Unit):Instruction = new Instruction {
    override def execute(machine:VirtualMachine) = f(machine)
  }
}

sealed abstract class StackValue(val stringRepresentation: () => String)

sealed abstract class NumericStackValue(val value:Double) extends StackValue(value.toString)
case class RealNumber(n:Double) extends NumericStackValue(n)
case class NaturalNumber(n:Int) extends NumericStackValue(n){
  override val stringRepresentation = () => n.toString()
}

case class StringValue(s:String) extends StackValue(s.toString)
case class BooleanValue(b:Boolean) extends StackValue(b.toString)
