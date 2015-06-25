package com.besuikerd.autologistics.common.tile.traits


import java.io.{DataInput, DataOutput}
import com.besuikerd.autologistics.common.lib.dsl.AutoLogisticsParser
import com.besuikerd.autologistics.common.lib.dsl.vm._
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.{NativeFunctionPrintln, NativeFunctionPrint, NativeFunctionKeys, NativeFunctionLength}
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue._
import com.besuikerd.autologistics.common.tile._
import com.besuikerd.autologistics.common.tile.TileEntityMod
import com.google.common.base.{Predicates}
import com.google.common.io.ByteStreams
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.nbt._
import net.minecraft.util.{AxisAlignedBB, ChatComponentText}
import scala.collection.JavaConversions._
import java.util.{List => JList}

trait TileVirtualMachine extends TileEntityMod
{
  val virtualMachine = new DefaultVirtualMachine

  virtualMachine.addNative("length", NativeFunctionLength.instance)
  virtualMachine.addNative("keys", NativeFunctionKeys.instance)
  virtualMachine.addNative("print", NativeFunctionPrint.instance)
  virtualMachine.addNative("println", NativeFunctionPrintln.instance)
  virtualMachine.addNative("say", nativeSay)

  val nativeSay: ScalaNativeFunction = { (vm, args) =>
    val players = this.getEntitiesWithinRange[EntityPlayer](10)
    for{
      arg <- args
      player <- players
    } {
      player.addChatComponentMessage(new ChatComponentText(arg.stringRepresentation))
    }
    NilValue.instance
  }

  abstract override def readFromNBT(compound: NBTTagCompound): Unit = {
    super.readFromNBT(compound)
    readVMFromNBT(compound)
    println("read from nbt" + compound)
  }

  override def writeToNBT(compound: NBTTagCompound): Unit = {
    super.writeToNBT(compound)
    writeVMToNBT(compound)
  }

  def writeVMToNBT(compound:NBTTagCompound): Unit = {
    val output = ByteStreams.newDataOutput()
    virtualMachine.serialize(output)
    compound.setTag("vm", new NBTTagByteArray(output.toByteArray))
  }

  def readVMFromNBT(compound: NBTTagCompound): Unit = {
    val input = ByteStreams.newDataInput(compound.getByteArray("vm"))
    virtualMachine.deserialize(input);
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
}

