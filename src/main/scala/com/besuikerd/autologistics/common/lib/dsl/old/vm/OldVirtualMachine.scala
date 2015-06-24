package com.besuikerd.autologistics.common.lib.dsl.old.vm

import com.besuikerd.autologistics.common.lib.collection.OldStack

import scala.collection.mutable.{ArrayBuffer, Map => MMap}
import scala.util.control.Breaks._


class OldVirtualMachine {
  import OldVirtualMachine._

  val stack = OldStack[StackValue]()
  val scopes = OldStack[Closure]()
  val instructions = OldStack[Instruction]()

  val globals = MMap[String, StackValue]()

  val natives = MMap[String, OldNativeFunction]()

  load(Nil)

  def addNative(name:String, f:OldNativeFunction):Unit = {
    natives.put(name, f)
  }

  def addNativePartial(name:String)(f:List[StackValue] => StackValue):Unit = {
    addNative(name, f)
  }

  def cycle(): Unit ={
    instructions.pop().execute(this)
  }

  def run(cycles:Int): Int = {
    var i = 0
    while(!isTerminated() && i < cycles){
      cycle()
      i = i + 1
    }
    i
  }

  def isTerminated(): Boolean = instructions.isEmpty || isErrorState()

  def isErrorState(): Boolean = instructions.nonEmpty && instructions.top().isInstanceOf[Crash]

  def load(program:List[Instruction]) = {
    clear()
    //push empty global closure on the stack
    scopes.push(Closure(List(), globals, program.reverse))

    instructions ::= program
  }

  def clear(): Unit = {
    stack.clear()
    scopes.clear()
    instructions.clear()
  }

//  def get(name:String):Option[StackValue] = scopes.map(_.free.get(name)).find(_.isDefined) match{
//    case Some(Some(value)) => Some(value)
//    case Some(None) => None
//    case None => None
//  }

  def get(name:String):Option[StackValue] = scopes.map(_.free.get(name)).collectFirst {
    case Some(value) => value
  }.orElse(natives.get(name).map(_ => NativeFunctionValue(name)))

  def openScope():Unit = openScope(Closure(List(), MMap(), List()))
  def openScope(closure:Closure) = scopes.push(closure)
  def closeScope():Unit = scopes.pop()
  def pop() = stack.pop()
  def push(value:StackValue) = stack.push(value)
  def crash(msg:String) = instructions.push(Crash(msg))
}

object OldVirtualMachine{
  type OldNativeFunction = List[StackValue] => StackValue
}


sealed abstract class DefaultInstruction(val f:OldVirtualMachine => Unit) extends Instruction{
  override def execute(machine: OldVirtualMachine): Unit = f(machine)
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
    case NativeFunctionValue(name) => {
      machine.natives.get(name) match{
        case Some(f) => try{
          machine.push(f(args))
        } catch{
          case NativeFunctionException(e) => machine.crash(e)
        }
        case _ => machine.crash(s"native function not found: $name")
      }
    }

    case Closure(bindings, free, body) => {
      machine.instructions.push(CloseScope)
      machine.openScope()
      machine.instructions ::= body
      free.foreach(x => machine.scopes.top().free.put(x._1, x._2))
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
      case other => machine.crash(s"can not apply  ${other.stringRepresentation}")
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
    case other => machine.push(BooleanValue(machine.pop().equals(other)))
  }
)

object EQInstruction extends DefaultInstruction(machine => machine.push(BooleanValue(machine.pop().equals(machine.pop()))))
object NEQInstruction extends DefaultInstruction(machine => machine.push(BooleanValue(!machine.pop().equals(machine.pop()))))
object GTInstruction extends CompareInstruction(">", _ > _)
object GTEInstruction extends CompareInstruction(">=", _ >= _)
object LTInstruction extends CompareInstruction("<", _ < _)
object LTEInstruction extends CompareInstruction("<=", _ <=  _)

object AndInstruction extends DefaultInstruction(machine => machine.pop() match{
  case BooleanValue(b1) => machine.pop() match{
    case BooleanValue(b2) => machine.push(BooleanValue(b1 && b2))
    case otherwise => machine.crash(s"can not apply && to $otherwise")
  }
  case otherwise => machine.crash(s"can not apply && to $otherwise")
})

object OrInstruction extends DefaultInstruction(machine => machine.pop() match {
  case BooleanValue(b1) => machine.pop() match {
    case BooleanValue(b2) => machine.push(BooleanValue(b1 || b2))
    case otherwise => machine.crash(s"can not apply || to $otherwise")
  }
  case otherwise => machine.crash(s"can not apply || to $otherwise")
})

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

case class PushList(length:Int) extends DefaultInstruction(machine => {
  val list = ArrayBuffer[StackValue]()
  for(i <- 0 until length){
    list += machine.pop()
  }
  machine.push(ListValue(list))
})

object GetField extends DefaultInstruction(machine => {
  (machine.pop(), machine.pop()) match{
    case (StringValue(s), ObjectValue(mapping)) => {
      mapping.get(s) match{
        case Some(value) => machine.push(value)
        case None => machine.crash(s"field not found: $s")
      }
    }
    case (NaturalNumber(i), ListValue(list)) => {
      if(i < list.length){
        machine.push(list(i))
      } else{
        machine.crash(s"index out of bounds: $i")
      }
    }
    case (otherVal, otherIndex) => machine.crash(s"cannot get field $otherIndex from $otherVal")
  }
})

object PutField extends DefaultInstruction(machine => {
  (machine.pop(), machine.pop(), machine.pop()) match{
    case (value, StringValue(s), ObjectValue(mapping)) => {
      mapping.put(s, value)
    }
    case (value, NaturalNumber(i), ListValue(list)) => {
      for(j <- list.length to i){
        list += NilValue
      }
      list.update(i, value)
    }
    case (otherVal, _, otherIndex) => machine.crash(s"cannot put field $otherIndex to $otherVal")
  }
})
/*
case class GetIndex(level:Int) extends DefaultInstruction(machine => {
  machine.pop() match{
    case obj@ObjectValue(bindings) => {
      breakable{
        var current:ObjectValue = obj
        for(i <- 0 until level){
          machine.pop() match{
            case StringValue(s) => current.mapping.get(s) match{
              case Some(other) if i == level - 1 => {
                machine.push(other)
                break
              }
              case Some(o:ObjectValue) => {
                current = o
              }
              case Some(other) => {
                machine.crash(s"indexing a non-object value")
                break
              }
              case None => {
                machine.crash(s"could not find index: $s")
                break
              }
            }
            case other => {
              machine.crash(s"cannot index objects with $other")
              break
            }
          }
        }
      }
    }
    case ListValue(bindings) => {
      breakable{
        //var current:ListValue
      }
    }
    case other => machine.crash(s"cannot get index for $other")
  }
})
*/

case class Load(instructions:List[Instruction]) extends DefaultInstruction(machine => {
  machine.instructions ::= instructions
})

case class Branch(left:List[Instruction], right:List[Instruction]) extends DefaultInstruction(machine => {
  machine.pop() match{
    case BooleanValue(b) => if(b) machine.instructions ::= left else machine.instructions ::= right
    case otherwise => machine.crash(s"cannot branch with ${otherwise.stringRepresentation}")
  }
})

case class RepeatedBranch(left:List[Instruction], right:List[Instruction]) extends DefaultInstruction(machine => {
  machine.pop() match{
    case BooleanValue(b) => if(b) {
      machine.instructions.push(RepeatedBranch(left, right))
      machine.instructions ::= left
    } else machine.instructions ::= right
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

case class ObjectValue(mapping:MMap[String, StackValue]) extends StackValue{
  override def stringRepresentation = {
    mapping.toList.map{case (k,v) => s"$k = ${v.stringRepresentation}"}.mkString("{", ", ", "}")
  }
  def copy():ObjectValue = {
    val mappingCopy = MMap[String, StackValue]()
    mappingCopy ++= mapping.mapValues {
      case o:ObjectValue => o.copy()
      case l:ListValue => l.copy()
      case other => other
    }
    ObjectValue(mappingCopy)
  }
}
object ObjectValue{
  def apply():ObjectValue = ObjectValue(MMap())
}

case class ListValue(list:ArrayBuffer[StackValue]) extends StackValue{
  override def stringRepresentation: String = list.mkString("[", ",", "]")

  def copy():ListValue = {
    ListValue(ArrayBuffer(list:_*))
  }
}

object ListValue{
  def apply():ListValue = ListValue(ArrayBuffer())
}


case class Closure(bindings: List[String], free:MMap[String, StackValue], body:List[Instruction]) extends StackValue{override def stringRepresentation = "Closure"}

case class NativeFunctionValue(name:String) extends StackValue{override def stringRepresentation = s"<$name>"}
case class NativeFunctionException(msg:String) extends RuntimeException(msg)