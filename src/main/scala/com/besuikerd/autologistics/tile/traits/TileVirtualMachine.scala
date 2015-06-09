package com.besuikerd.autologistics.tile.traits


import com.besuikerd.autologistics.core.dsl.AutoLogisticsParser
import com.besuikerd.autologistics.lib.dsl.vm._
import com.besuikerd.autologistics.tile.TileEntityMod
import com.google.common.base.{Predicates, Predicate}
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.server.gui.IUpdatePlayerListBox
import net.minecraft.util.ChatComponentText
import scala.collection.JavaConversions._

trait TileVirtualMachine extends TileEntityMod
{
  val virtualMachine = new VirtualMachine

  virtualMachine.addNative("print", nativePrint)
  virtualMachine.addNative("println", nativePrintln)
  virtualMachine.addNative("say", nativeSay)

  def nativePrint(values:List[StackValue]): StackValue = {
    values.foreach(print)
    NilValue
  }

  def nativePrintln(values:List[StackValue]): StackValue = {
    values.foreach(println)
    NilValue
  }

  def nativeSay(values:List[StackValue]): StackValue = {
    getWorld.getPlayers(classOf[EntityPlayerMP], Predicates.alwaysTrue()).foreach(x => {
      values.foreach(value => x.asInstanceOf[EntityPlayerMP].addChatComponentMessage(new ChatComponentText(value.stringRepresentation)))
    })
    NilValue
  }

  def checkArgCount[A](values:List[A], n:Int): Unit ={
    if(values.size < n){
      throw new NativeFunctionException(s"expecting $n arguments")
    }
  }


  override def readFromNBT(compound: NBTTagCompound): Unit = {
    super.readFromNBT(compound)
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

  /*
  private def serializeInstruction(instruction:Instruction): NBTTagList ={
    instruction match {
      case Pop => nbtList("pop")
      case Push(value) => nbtList("push", serializeStackValue(value))
      case OpenScope => nbtList("open")
      case CloseScope => nbtList("close")
      case Crash(msg) => nbtList("crash", msg)
      case Push(value) => nbtList("push", serializeStackValue(value))
      case Get => nbtList("get")
      case Put(name) => nbtList("put", name)
      case AddInstruction => nbtList("add")
      case SubInstruction => nbtList("sub")
      case MulInstruction => nbtList("mul")
      case DivInstruction => nbtList("div")
      case ModInstruction => nbtList("mod")
      case GTInstruction => nbtList("gt")
      case GTEInstruction => nbtList("gte")
      case LTInstruction => nbtList("lt")
      case LTEInstruction => nbtList("lte")
      case EQInstruction => nbtList("eq")
      case NEQInstruction => nbtList("neq")
      case AndInstruction => nbtList("and")
      case OrInstruction => nbtList("or")
      case PushClosure(name, bindings, free, body) => nbtList(
        "pc",
        name.getOrElse("_"),
        nbtList(bindings.map(string2NBT):_*),
        nbtList(free.map(string2NBT):_*),
        nbtList(body.map(serializeInstruction):_*)
      )

    }
  }
  */

  /*
  private def readInstruction(instruction:Instruction, list: NBTTagList): Unit = {

  }

  private def serializeStackValue(stackValue:StackValue): NBTBase = {

  }

  override def writeToNBT(compound: NBTTagCompound): Unit = {
    super.writeToNBT(compound)
  }
  */
}

