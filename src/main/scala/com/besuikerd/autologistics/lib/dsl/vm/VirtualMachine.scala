package com.besuikerd.autologistics.lib.dsl.vm

import org.scalatest.fixture
import scala.collection.mutable.{Map => MMap}
import com.besuikerd.autologistics.lib.collection.Stack
import scala.util.control.Breaks._


class VirtualMachine {

  val stack = Stack[StackValue]()
  val scopes = Stack[Closure]()
  val instructions = Stack[Instruction]()

  load(Nil)

  def addNative(name:String, f:List[StackValue] => StackValue):Unit = {
    scopes.last.free.put(name, NativeFunction(name, f))
  }

  def addNativePartial(name:String)(f:List[StackValue] => StackValue):Unit = {
    addNative(name, f)
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

  def isErrorState(): Boolean = instructions.nonEmpty && instructions.top().isInstanceOf[Crash]

  def load(program:List[Instruction]) = {
    stack.clear()
    scopes.clear()

    //push empty global closure on the stack
    scopes.push(Closure(List(), MMap(), program.reverse))
    instructions.clear()
    instructions ::= program
  }

  def get(name:String):Option[StackValue] = scopes.map(_.free.get(name)).find(_.isDefined) match{
    case Some(Some(value)) => Some(value)
    case Some(None) => None
    case None => None
  }

  def openScope():Unit = openScope(Closure(List(), MMap(), List()))
  def openScope(closure:Closure) = scopes.push(closure)
  def closeScope():Unit = scopes.pop()
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

object OpenScope extends DefaultInstruction(_.openScope())
object CloseScope extends DefaultInstruction(_.closeScope())
case class Crash(msg:String) extends DefaultInstruction(_.crash(msg))

case class Push(value:StackValue) extends DefaultInstruction(_.stack.push(value))

case class Get(s:String) extends DefaultInstruction(machine =>{
  machine.get(s) match{
    case Some(Recurse) => machine.push(machine.scopes.find(c => c.free.exists{case (k,v) => k.equals(s) && v.equals(Recurse)}).get)
    case Some(value) => machine.push(value)
    case None => machine.crash(s"Value not found: $s")
  }
})

case class Call(argCount:Int) extends DefaultInstruction(machine => {
  val args = (for(_ <- 0 until argCount) yield machine.pop()).toList.reverse
  machine.pop() match{
    case NativeFunction(name, f) => machine.push(f(args))

    case Closure(bindings, free, body) => {
      machine.instructions.push(CloseScope)
      machine.openScope()
      machine.instructions ::= body
      bindings.zip(args).foreach{case (name, arg) => machine.scopes.top().free.put(name, arg)}
    }
    case other => machine.crash(s"can not call ${other.stringRepresentation}")
  }
})

case class Put(s:String) extends DefaultInstruction(machine => {
  machine.scopes.top().free.put(s, machine.pop())
})

sealed abstract class NumericInstruction(f: (Double, Double) => Double) extends DefaultInstruction(machine =>
  machine.pop() match {
    case NaturalNumber(m) => machine.pop() match{
      case NaturalNumber(n) => machine.push(NaturalNumber(f(n,m).toInt))
      case n:NumericStackValue => machine.push(RealNumber(f(n.value, m)))
      case StringValue(s) => machine.push(StringValue(s + m.toString))
      case other => machine.crash(s"can not apply  ${other.getClass.getSimpleName}")
    }
    case RealNumber(m) => machine.pop() match {
      case n:NumericStackValue => machine.push(RealNumber(f(n.value, m)))
      case StringValue(s) => machine.push(StringValue(m.toString + s))
      case other => machine.crash(s"can not add ${other.getClass.getSimpleName}")
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
  machine.pop() match {
    case m:NumericStackValue => machine.pop() match{
      case n:NumericStackValue => machine.push(BooleanValue(f(n.value, m.value)))
      case other => machine.crash(s"can not apply '$name' to ${other.stringRepresentation}")
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

case class PushClosure(name:Option[String], bindings:List[String], free:List[String], body:List[Instruction]) extends DefaultInstruction(machine => {
  val optFrees = free.map(x => (x, machine.get(x)))
  optFrees.find(_._2.isEmpty) match{
    case Some((free, _)) if !name.map(_.equals(free)).getOrElse(false) => machine.crash(s"could not bind free variable: $free")
    case otherwise => {
      val frees = free.zip(optFrees.map(_._2.getOrElse(Recurse))).toMap
      machine.push(Closure(bindings, MMap() ++ frees, body))
    }
  }
})

case class PushObject(bindings:List[String]) extends DefaultInstruction(machine => {
  val fields = MMap[String, StackValue]()
  bindings.foreach(name => fields.put(name, machine.pop()))
  machine.push(ObjectValue(fields))
})

case class Select(fields:List[String]) extends DefaultInstruction(machine => {
  machine.pop() match{
    case ObjectValue(bindings) => {
      var mFields = fields
      var mBindings = bindings
      breakable {
        while (mFields.nonEmpty) {
          mBindings.get(mFields.head) match {
            case Some(ObjectValue(bindings)) => mBindings = bindings
            case Some(other) if mFields.size > 1 => {
              machine.crash(s"cannot select fields from $other")
              break()
            }
            case Some(other) => machine.push(other)
            case None => {
              machine.crash("cannot find value: " + mFields.head)
              break()
            }
          }
          mFields = mFields.tail
        }
      }
    }
    case other => machine.crash(s"cannot select fields from $other")
  }
})

case class UpdateField(fields:List[String]) extends DefaultInstruction(machine => {
  machine.pop() match {
    case ObjectValue(bindings) => {
      var mFields = fields
      var mBindings = bindings
      breakable {

        while (mFields.nonEmpty) {
          mBindings.get(mFields.head) match {
            case Some(ObjectValue(bindings)) => mBindings = bindings
            case Some(other) if mFields.size > 1 => {
              machine.crash(s"cannot select fields from $other")
              break()
            }
            case None if mFields.size > 1 => {
              machine.crash("cannot find value: " + mFields.head)
              break()
            }
            case other => { //last field
              mBindings.put(mFields.head, machine.pop())
            }
          }
          mFields = mFields.tail
        }
      }
    }
    case other => machine.crash(s"cannot assign field to $other")
  }
})

case class Branch(left:List[Instruction], right:List[Instruction]) extends DefaultInstruction(machine => {
  machine.pop() match{
    case BooleanValue(b) => if(b) machine.instructions ::= left else machine.instructions ::= right
    case otherwise => machine.crash(s"cannot branch with ${otherwise.stringRepresentation}")
  }
})

sealed abstract class StackValue{
  def stringRepresentation:String
}

sealed abstract class NumericStackValue(val value:Double) extends StackValue
case class RealNumber(n:Double) extends NumericStackValue(n){override def stringRepresentation = n.toString}
case class NaturalNumber(n:Int) extends NumericStackValue(n){override def stringRepresentation = n.toString}

case class StringValue(s:String) extends StackValue{override def stringRepresentation = s}
case class BooleanValue(b:Boolean) extends StackValue{override def stringRepresentation = b.toString}
object NilValue extends StackValue{override def stringRepresentation = "null"}
object Recurse extends StackValue{override def stringRepresentation = "_recursive_call"}

case class ObjectValue(mapping:MMap[String, StackValue]) extends StackValue{override def stringRepresentation = "TODO: make stringrep()"}

case class Closure(bindings: List[String], free:MMap[String, StackValue], body:List[Instruction]) extends StackValue{override def stringRepresentation = "Closure"}

case class NativeFunction(name:String, f:List[StackValue] => StackValue) extends StackValue{override def stringRepresentation = s"<$name>"}