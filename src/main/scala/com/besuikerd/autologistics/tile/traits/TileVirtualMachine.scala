package com.besuikerd.autologistics.tile.traits


import com.besuikerd.autologistics.core.dsl.AutoLogisticsParser
import com.besuikerd.autologistics.lib.dsl.vm._
import com.besuikerd.autologistics.tile.TileEntityMod
import com.google.common.base.{Predicates}
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.nbt.{NBTBase, NBTTagList, NBTTagCompound}
import net.minecraft.util.ChatComponentText
import scala.collection.JavaConversions._
import com.besuikerd.autologistics.lib.nbt.NBTHelper._

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
  }

  override def writeToNBT(compound: NBTTagCompound): Unit = {
    super.writeToNBT(compound)
    writeVMToNBT(compound)
  }

  def writeVMToNBT(compound:NBTTagCompound): Unit = {
    val stack = nbtList(virtualMachine.stack.map(serializeStackValue):_*)
    val instructions = nbtList(virtualMachine.instructions.map(serializeInstruction):_*)
    val scopes = nbtList(virtualMachine.scopes.map(serializeStackValue):_*)

    compound.setTag("stack", stack)
    compound.setTag("instructions", instructions)
    compound.setTag("scopes", scopes)
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




  def serializeInstruction(instruction:Instruction): NBTTagCompound ={
    val k = "i"
    instruction match {
      case Pop => nbtMap(k -> "pop")
      case Push(value) => nbtMap(k -> "push", "value" -> serializeStackValue(value))
      case OpenScope => nbtMap(k -> "open")
      case CloseScope => nbtMap(k -> "close")
      case Crash(msg) => nbtMap(k -> "crash", "msg" -> msg)
      case Get(name) => nbtMap(k -> "get", "name" -> name)
      case Put(name) => nbtMap(k -> "put", "name" -> name)
      case AddInstruction => nbtMap(k -> "add")
      case SubInstruction => nbtMap(k -> "sub")
      case MulInstruction => nbtMap(k -> "mul")
      case DivInstruction => nbtMap(k -> "div")
      case ModInstruction => nbtMap(k -> "mod")
      case GTInstruction => nbtMap(k -> "gt")
      case GTEInstruction => nbtMap(k -> "gte")
      case LTInstruction => nbtMap(k -> "lt")
      case LTEInstruction => nbtMap(k -> "lte")
      case EQInstruction => nbtMap(k -> "eq")
      case NEQInstruction => nbtMap(k -> "neq")
      case AndInstruction => nbtMap(k -> "and")
      case OrInstruction => nbtMap(k -> "or")
      case PushClosure(name, bindings, free, body) => nbtMap(
        k -> "pc",
        "name" -> string2NBT(name.getOrElse("_")),
        "bindings" -> nbtList(bindings.map(string2NBT):_*),
        "free" -> nbtList(free.map(string2NBT):_*),
        "body" -> nbtList(body.map(serializeInstruction):_*)
      )
      case Branch(y, n) => nbtMap(k -> "br", "y" -> nbtList(y.map(serializeInstruction):_*), "n" -> nbtList(n.map(serializeInstruction):_*))
      case RepeatedBranch(y, n) => nbtMap(k -> "rbr", "y" -> nbtList(y.map(serializeInstruction):_*), "n" -> nbtList(n.map(serializeInstruction):_*))
      case Call(n) => nbtMap(k -> "call", "n" -> n)
      case GetField => nbtMap(k -> "gf")
      case PutField => nbtMap(k -> "pf")
      case Load(instructions) => nbtMap(k -> "load", "instr" -> nbtList(instructions.map(serializeInstruction):_*))
      case PushList(length) => nbtMap(k -> "pushl", "n" -> length)
      case PushObject(bindings) => nbtMap(k -> "pusho", "map" -> nbtList(bindings.map(string2NBT):_*))
    }
  }

  private def readInstruction(instruction:Instruction, list: NBTTagList): Unit = {

  }

  def serializeStackValue(stackValue:StackValue): NBTTagCompound = {
    val k = "v"
    stackValue match {
      case NilValue => nbtMap(k -> "null")
      case BooleanValue(b) => nbtMap(k -> (if(b) "true" else "false"))
      case NaturalNumber(n) => nbtMap(k -> n)
      case RealNumber(r) => nbtMap(k -> r)
      case StringValue(s) => nbtMap("string" -> s)
      case Closure(bindings, free, body) => nbtMap(
        k -> "closure",
        "binding" -> nbtList(bindings.map(string2NBT):_*),
        "free" -> pairs2NBT(free.mapValues(serializeStackValue).toIterable),
        "body" -> nbtList(body.map(serializeInstruction):_*)
      )
      case ListValue(list) => nbtMap("list" -> nbtList(list.map(serializeStackValue):_*))
      case NativeFunctionValue(name) => nbtMap(k -> "nat", "name" -> name)
      case ObjectValue(mapping) => nbtMap(k -> "obj", "map" -> pairs2NBT(mapping.mapValues(serializeStackValue).toIterable))
      case Recurse => nbtMap(k -> "recurse")
    }
  }
}

