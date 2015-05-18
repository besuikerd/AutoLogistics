package com.besuikerd.autologistics.lib.dsl.vm

import org.scalatest.fixture

import scala.collection.mutable.Stack
import scala.collection.mutable.Map

class VirtualMachine {
  type Scope = Map[String, StackValue]

  val stack = Stack[StackValue]()
  val globalScope:Scope = Map[String, StackValue]()
  val scopes = Stack[Scope]()
  val instructions = Stack[Instruction]()

  reset()
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

  def addNative(name:String, f:List[StackValue] => StackValue):Unit = {
    globalScope.put(name, NativeFunction(name, f))
  }

  def addNativePartial(name:String)(f:List[StackValue] => StackValue):Unit = {
    addNative(name, f)
  }

  def reset(): Unit ={
    stack.clear()
    scopes.clear()
    scopes.push(globalScope)
    instructions.clear()
  }

  def cycle(): Unit ={
    instructions.pop().execute(this)
  }

  def run(cycles:Int): Unit = {
    var i = 0
    while(!isTerminated() && i < cycles){
      cycle()
      i = i + 1
    }
  }

  def isTerminated(): Boolean = instructions.isEmpty || isErrorState()

  def isErrorState(): Boolean = !instructions.isEmpty && instructions.top.isInstanceOf[Crash]

  def load(program:List[Instruction]) = {
    reset()
    instructions.pushAll(program.reverse)
  }

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

case class Call(argCount:Int) extends DefaultInstruction(machine => {
  val args = (for(_ <- 0 until argCount) yield machine.pop()).toList
  machine.pop() match{
    case NativeFunction(name, f) => machine.push(f(args))
    case other => machine.crash(s"can not call ${other.stringRepresentation}")
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
      case other => machine.crash(s"can not apply  ${other.getClass.getSimpleName}")
    }
    case StringValue(s) => machine.push(StringValue(s + machine.pop().stringRepresentation))
    case other => machine.crash(s"can not apply '${getClass.getSimpleName}' to $other")
  }
)

object AddInstruction extends NumericInstruction(_ + _)
object SubInstruction extends NumericInstruction(_ - _)
object MulInstruction extends NumericInstruction(_ * _)
object DivInstruction extends NumericInstruction(_ / _)
object ModInstruction extends NumericInstruction(_ % _)

sealed abstract class CompareInstruction(name:String, f: (Double, Double) => Boolean) extends DefaultInstruction(machine =>
  machine.pop match {
    case m:NumericStackValue => machine.pop match{
      case n:NumericStackValue => machine.push(BooleanValue(f(m.value, n.value)))
    }
    case other => {
      machine.crash(s"can not apply '$name' to ${other.stringRepresentation}")
    }
  }
)

object EQInstruction extends CompareInstruction("==", _ == _)
object NEQInstruction extends CompareInstruction("!=", _ != _)
object GTInstruction extends CompareInstruction(">", _ > _)
object GTEInstruction extends CompareInstruction(">=", _ >= _)
object LTInstruction extends CompareInstruction("<", _ < _)
object LTEInstruction extends CompareInstruction("<=", _ <=  _)

sealed abstract class StackValue(val stringRepresentation: String)

sealed abstract class NumericStackValue(val value:Double) extends StackValue(value.toString)
case class RealNumber(n:Double) extends NumericStackValue(n)
case class NaturalNumber(n:Int) extends NumericStackValue(n){
  override val stringRepresentation = n.toString
}

case class StringValue(s:String) extends StackValue(s.toString)
case class BooleanValue(b:Boolean) extends StackValue(b.toString)
object NilValue extends StackValue("null")

case class NativeFunction(name:String, f:List[StackValue] => StackValue) extends StackValue(s"<$name>")