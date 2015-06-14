package com.besuikerd.autologistics.common.tile.traits


import java.io.{DataInput, DataOutput}
import com.besuikerd.autologistics.common.lib.collection.Stack
import com.besuikerd.autologistics.common.lib.dsl.AutoLogisticsParser
import com.besuikerd.autologistics.common.lib.dsl.vm._
import com.besuikerd.autologistics.common.tile.TileEntityMod
import com.google.common.base.{Predicates}
import com.google.common.io.ByteStreams
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.nbt._
import net.minecraft.util.ChatComponentText
import net.minecraftforge.fml.common.network.ByteBufUtils
import scala.annotation.switch
import scala.collection.JavaConversions._
import com.besuikerd.autologistics.common.lib.nbt.NBTHelper._
import scala.collection.mutable.{Map => MMap, ArrayBuffer}

trait TileVirtualMachine extends TileEntityMod
{
  val virtualMachine = new VirtualMachine

  virtualMachine.addNative("length", nativeLength)
  virtualMachine.addNative("print", nativePrint)
  virtualMachine.addNative("println", nativePrintln)
  virtualMachine.addNative("say", nativeSay)

  def nativePrint(values:List[StackValue]): StackValue = {
    values.foreach(x => print(x.stringRepresentation))
    NilValue
  }

  def nativePrintln(values:List[StackValue]): StackValue = {
    values.foreach(x => println(x.stringRepresentation))
    NilValue
  }

  def nativeSay(values:List[StackValue]): StackValue = {
    getWorld.getPlayers(classOf[EntityPlayerMP], Predicates.alwaysTrue()).foreach(x => {
      values.foreach(value => x.asInstanceOf[EntityPlayerMP].addChatComponentMessage(new ChatComponentText(value.stringRepresentation)))
    })
    NilValue
  }

  def nativeLength(values:List[StackValue]): StackValue = {
    values match {
      case ListValue(list) :: _ => NaturalNumber(list.length)
      case other => throw NativeFunctionException(s"cannot get length of $other")
    }
  }

  def checkArgCount[A](values:List[A], n:Int): Unit ={
    if(values.size < n){
      throw new NativeFunctionException(s"expecting $n arguments")
    }
  }


  abstract override def readFromNBT(compound: NBTTagCompound): Unit = {
    super.readFromNBT(compound)
    readVMFromNBT(compound)
    println("read from nbt")
  }

  override def writeToNBT(compound: NBTTagCompound): Unit = {
    super.writeToNBT(compound)
    writeVMToNBT(compound)
  }

  def writeVMToNBT(compound:NBTTagCompound): Unit = {
    val stackOutput = ByteStreams.newDataOutput()
    stackOutput.writeInt(virtualMachine.stack.length)
    virtualMachine.stack.foreach(writeStackValue(_, stackOutput))

    val instructionsOutput = ByteStreams.newDataOutput()
    instructionsOutput.writeInt(virtualMachine.instructions.length)
    virtualMachine.instructions.foreach(writeInstruction(_, instructionsOutput))

    val scopesOutput = ByteStreams.newDataOutput()
    scopesOutput.writeInt(virtualMachine.scopes.length)
    virtualMachine.scopes.foreach(writeStackValue(_, scopesOutput))

    compound.setTag("stack", new NBTTagByteArray(stackOutput.toByteArray))
    compound.setTag("instructions", new NBTTagByteArray(instructionsOutput.toByteArray))
    compound.setTag("scopes", new NBTTagByteArray((scopesOutput.toByteArray)))
  }

  def readVMFromNBT(compound: NBTTagCompound): Unit = {
    virtualMachine.clear()
    val stackInput = ByteStreams.newDataInput(compound.getByteArray("stack"))
    virtualMachine.stack ::= (for(_ <- 0 until stackInput.readInt()) yield readStackValue(stackInput)).toList

    val instructionInput = ByteStreams.newDataInput(compound.getByteArray("instructions"))
    virtualMachine.instructions ::= (for (_ <- 0 until instructionInput.readInt()) yield readInstruction(instructionInput)).toList

    val closureInput = ByteStreams.newDataInput(compound.getByteArray("scopes"))
    virtualMachine.scopes ::= (for(_ <- 0 until closureInput.readInt()) yield readStackValue(closureInput).asInstanceOf[Closure]).toList
  }



  def load(program:String): Unit ={
    AutoLogisticsParser.parse(AutoLogisticsParser.parser, program) match{
      case AutoLogisticsParser.Success(ast, _) => {
        val instructions = CodeGenerator.generate(ast)
        println(ast)
        virtualMachine.load(instructions)
      }
      case AutoLogisticsParser.NoSuccess(error, _) => {
        println("Failed to load program: " + error)
      }
    }
  }




//  def serializeInstruction(instruction:Instruction): NBTTagCompound ={
//    val k = "i"
//    instruction match {
//      case Pop => nbtMap(k -> "pop")
//      case Push(value) => nbtMap(k -> "push", "value" -> serializeStackValue(value))
//      case OpenScope => nbtMap(k -> "open")
//      case CloseScope => nbtMap(k -> "close")
//      case Crash(msg) => nbtMap(k -> "crash", "msg" -> msg)
//      case Get(name) => nbtMap(k -> "get", "name" -> name)
//      case Put(name) => nbtMap(k -> "put", "name" -> name)
//      case AddInstruction => nbtMap(k -> "add")
//      case SubInstruction => nbtMap(k -> "sub")
//      case MulInstruction => nbtMap(k -> "mul")
//      case DivInstruction => nbtMap(k -> "div")
//      case ModInstruction => nbtMap(k -> "mod")
//      case GTInstruction => nbtMap(k -> "gt")
//      case GTEInstruction => nbtMap(k -> "gte")
//      case LTInstruction => nbtMap(k -> "lt")
//      case LTEInstruction => nbtMap(k -> "lte")
//      case EQInstruction => nbtMap(k -> "eq")
//      case NEQInstruction => nbtMap(k -> "neq")
//      case AndInstruction => nbtMap(k -> "and")
//      case OrInstruction => nbtMap(k -> "or")
//      case PushClosure(name, bindings, free, body) => nbtMap(
//        k -> "pc",
//        "name" -> string2NBT(name.getOrElse("_")),
//        "bindings" -> nbtList(bindings.map(string2NBT):_*),
//        "free" -> nbtList(free.map(string2NBT):_*),
//        "body" -> nbtList(body.map(serializeInstruction):_*)
//      )
//      case Branch(y, n) => nbtMap(k -> "br", "y" -> nbtList(y.map(serializeInstruction):_*), "n" -> nbtList(n.map(serializeInstruction):_*))
//      case RepeatedBranch(y, n) => nbtMap(k -> "rbr", "y" -> nbtList(y.map(serializeInstruction):_*), "n" -> nbtList(n.map(serializeInstruction):_*))
//      case Call(n) => nbtMap(k -> "call", "n" -> n)
//      case GetField => nbtMap(k -> "gf")
//      case PutField => nbtMap(k -> "pf")
//      case Load(instructions) => nbtMap(k -> "load", "instr" -> nbtList(instructions.map(serializeInstruction):_*))
//      case PushList(length) => nbtMap(k -> "pushl", "n" -> length)
//      case PushObject(bindings) => nbtMap(k -> "pusho", "map" -> nbtList(bindings.map(string2NBT):_*))
//    }
//  }

  def writeInstruction(instruction:Instruction, out:DataOutput): Unit = {
    instruction match{
      case Pop => out.writeByte(OpCode.Pop.id)
      case Push(value) => {
        out.write(OpCode.Push.id)
        writeStackValue(value, out)
      }
      case OpenScope => out.writeByte(OpCode.OpenScope.id)
      case CloseScope => out.writeByte(OpCode.CloseScope.id)
      case Crash(msg) => {
        out.write(OpCode.Crash.id)
        out.writeUTF(msg)
      }
      case Get(name) => {
        out.write(OpCode.Get.id)
        out.writeUTF(name)
      }
      case Put(name) =>{
        out.write(OpCode.Put.id)
        out.writeUTF(name)
      }
      case AddInstruction => out.write(OpCode.Add.id)
      case SubInstruction => out.write(OpCode.Sub.id)
      case MulInstruction => out.write(OpCode.Mul.id)
      case DivInstruction => out.write(OpCode.Div.id)
      case ModInstruction => out.write(OpCode.Mod.id)
      case GTInstruction => out.write(OpCode.Gt.id)
      case GTEInstruction => out.write(OpCode.Gte.id)
      case LTInstruction => out.write(OpCode.Lt.id)
      case LTEInstruction => out.write(OpCode.Lte.id)
      case EQInstruction => out.write(OpCode.Eq.id)
      case NEQInstruction => out.write(OpCode.Neq.id)
      case AndInstruction => out.write(OpCode.And.id)
      case OrInstruction => out.write(OpCode.Or.id)
      case PushClosure(name, bindings, free, body) => {
        out.write(OpCode.PushClosure.id)
        out.writeUTF(name.getOrElse("_"))
        out.writeInt(bindings.length)
        bindings.foreach(out.writeUTF)
        out.writeInt(free.length)
        free.foreach(out.writeUTF)
        out.writeInt(body.length)
        body.foreach(writeInstruction(_, out))
      }
      case Branch(y, n) => {
        out.write(OpCode.Branch.id)
        out.writeInt(y.length)
        y.foreach(writeInstruction(_, out))
        out.writeInt(n.length)
        n.foreach(writeInstruction(_, out))
      }
      case RepeatedBranch(y, n) => {
        out.write(OpCode.RepeatedBranch.id)
        out.writeInt(y.length)
        y.foreach(writeInstruction(_, out))
        out.writeInt(n.length)
        n.foreach(writeInstruction(_, out))
      }
      case Call(n) => {
        out.write(OpCode.Call.id)
        out.writeInt(n)
      }
      case GetField => out.write(OpCode.GetField.id)
      case PutField => out.write(OpCode.PutField.id)
      case Load(instructions) => {
        out.write(OpCode.Load.id)
        out.writeInt(instructions.length)
        instructions.foreach(writeInstruction(_, out))
      }
      case PushList(length) => {
        out.write(OpCode.PushList.id)
        out.writeInt(length)
      }
      case PushObject(bindings) => {
        out.write(OpCode.PushObject.id)
        out.writeInt(bindings.length)
        bindings.foreach(out.writeUTF)
      }
    }
  }

  object OpCode extends Enumeration{
    val
      Pop,
      Push,
      OpenScope,
      CloseScope,
      Crash,
      Get,
      Put,
      Add,
      Sub,
      Mul,
      Div,
      Mod,
      Gt,
      Gte,
      Lt,
      Lte,
      Eq,
      Neq,
      And,
      Or,
      PushClosure,
      Branch,
      RepeatedBranch,
      Call,
      GetField,
      PutField,
      Load,
      PushList,
      PushObject
    = Value
  }

  object ValueCode extends Enumeration{
    val
      Nil,
      True,
      False,
      Natural,
      Real,
      String,
      Closure,
      List,
      Native,
      Object,
      Recurse
    = Value
  }

  def readInstruction(in: DataInput): Instruction = {
    OpCode(in.readByte()) match {
      case OpCode.Pop => Pop
      case OpCode.Push => Push(readStackValue(in))
      case OpCode.OpenScope => OpenScope
      case OpCode.CloseScope => CloseScope
      case OpCode.Crash => Crash(in.readUTF())
      case OpCode.Get => Get(in.readUTF())
      case OpCode.Put => Put(in.readUTF())
      case OpCode.Add => AddInstruction
      case OpCode.Sub => SubInstruction
      case OpCode.Mul => MulInstruction
      case OpCode.Div => DivInstruction
      case OpCode.Mod => ModInstruction
      case OpCode.Gt => GTInstruction
      case OpCode.Gte => GTEInstruction
      case OpCode.Lt => LTInstruction
      case OpCode.Lte => LTEInstruction
      case OpCode.Eq => EQInstruction
      case OpCode.Neq => NEQInstruction
      case OpCode.And => AndInstruction
      case OpCode.Or => OrInstruction
      case OpCode.PushClosure => {
        val name = in.readUTF()
        val bindings = for(_ <- 0 until in.readInt()) yield in.readUTF()
        val free = for(_ <- 0 until in.readInt()) yield in.readUTF()
        val body = for(_ <- 0 until in.readInt()) yield readInstruction(in)
        PushClosure(if(name.equals("_")) None else Some(name), bindings.toList, free.toList, body.toList)
      }
      case OpCode.Branch => {
        val y = for(_ <- 0 until in.readInt()) yield readInstruction(in)
        val n = for(_ <- 0 until in.readInt()) yield readInstruction(in)
        Branch(y.toList, n.toList)
      }
      case OpCode.RepeatedBranch => {
        val y = for(_ <- 0 until in.readInt()) yield readInstruction(in)
        val n = for(_ <- 0 until in.readInt()) yield readInstruction(in)
        RepeatedBranch(y.toList, n.toList)
      }
      case OpCode.Call => Call(in.readInt())
      case OpCode.GetField => GetField
      case OpCode.PutField => PutField
      case OpCode.Load => {
        val instructions = for(_ <- 0 until in.readInt()) yield readInstruction(in)
        Load(instructions.toList)
      }
      case OpCode.PushList => PushList(in.readInt())
      case OpCode.PushObject => {
        val fields = for(_ <- 0 until in.readInt()) yield in.readUTF()
        PushObject(fields.toList)
      }
    }
  }

  def writeStackValue(value:StackValue, out:DataOutput): Unit = value match {
    case NilValue => out.write(ValueCode.Nil.id)
    case BooleanValue(b) => out.write(if(b) ValueCode.True.id else ValueCode.False.id)
    case NaturalNumber(n) => {
      out.write(ValueCode.Natural.id)
      out.writeInt(n)
    }
    case RealNumber(r) => {
      out.write(ValueCode.Real.id)
      out.writeDouble(r)
    }
    case StringValue(s) => {
      out.write(ValueCode.String.id)
      out.writeUTF(s)
    }
    case Closure(bindings, free, body) => {
      out.write(ValueCode.Closure.id)
      out.writeInt(bindings.length)
      bindings.foreach(out.writeUTF)
      out.writeInt(free.size)
      free.foreach{case (k,v) =>
        out.writeUTF(k)
        writeStackValue(v, out)
      }
      out.writeInt(body.length)
      body.foreach(writeInstruction(_, out))
    }
    case ListValue(list) => {
      out.write(ValueCode.List.id)
      out.writeInt(list.length)
      list.foreach(writeStackValue(_, out))
    }
    case NativeFunctionValue(name) => {
      out.write(ValueCode.Native.id)
      out.writeUTF(name)
    }
    case ObjectValue(mapping) => {
      out.write(ValueCode.Object.id)
      out.writeInt(mapping.size)
      mapping.foreach{case (k,v) =>
        out.writeUTF(k)
        writeStackValue(v, out)
      }
    }
    case Recurse => out.write(ValueCode.Recurse.id)
  }

  def readStackValue(in: DataInput): StackValue = {
    ValueCode(in.readByte()) match{
      case ValueCode.Nil => NilValue
      case ValueCode.True => BooleanValue(true)
      case ValueCode.False => BooleanValue(false)
      case ValueCode.Natural => NaturalNumber(in.readInt())
      case ValueCode.Real => RealNumber(in.readDouble())
      case ValueCode.String => StringValue(in.readUTF())
      case ValueCode.Closure => {
        val bindings = for(_ <- 0 until in.readInt()) yield in.readUTF()
        val free = for(_ <- 0 until in.readInt()) yield (in.readUTF(), readStackValue(in))
        val body = for(_ <- 0 until in.readInt()) yield readInstruction(in)
        Closure(bindings.toList, MMap[String, StackValue]() ++ free.toMap, body.toList)
      }
      case ValueCode.List => {
        val list = for(_ <- 0 until in.readInt()) yield readStackValue(in)
        ListValue(ArrayBuffer[StackValue]() ++ list.toList)
      }
      case ValueCode.Native => NativeFunctionValue(in.readUTF())
      case ValueCode.Object => {
        val values = for(_ <- 0 until in.readInt()) yield (in.readUTF(), readStackValue(in))
        ObjectValue(MMap[String, StackValue]() ++ values.toMap)
      }
      case ValueCode.Recurse => Recurse
    }
  }

//  def serializeStackValue(stackValue:StackValue): NBTTagCompound = {
//    val k = "v"
//    stackValue match {
//      case NilValue => nbtMap(k -> "null")
//      case BooleanValue(b) => nbtMap(k -> (if(b) "true" else "false"))
//      case NaturalNumber(n) => nbtMap(k -> n)
//      case RealNumber(r) => nbtMap(k -> r)
//      case StringValue(s) => nbtMap("string" -> s)
//      case Closure(bindings, free, body) => nbtMap(
//        k -> "closure",
//        "binding" -> nbtList(bindings.map(string2NBT):_*),
//        "free" -> pairs2NBT(free.mapValues(serializeStackValue).toIterable),
//        "body" -> nbtList(body.map(serializeInstruction):_*)
//      )
//      case ListValue(list) => nbtMap("list" -> nbtList(list.map(serializeStackValue):_*))
//      case NativeFunctionValue(name) => nbtMap(k -> "nat", "name" -> name)
//      case ObjectValue(mapping) => nbtMap(k -> "obj", "map" -> pairs2NBT(mapping.mapValues(serializeStackValue).toIterable))
//      case Recurse => nbtMap(k -> "recurse")
//    }
//  }
}

