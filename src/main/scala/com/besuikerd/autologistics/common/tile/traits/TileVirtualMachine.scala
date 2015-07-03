package com.besuikerd.autologistics.common.tile.traits


import java.io.{IOException, EOFException, DataInput, DataOutput}
import com.besuikerd.autologistics.common.BLogger
import com.besuikerd.autologistics.common.lib.dsl.AutoLogisticsParser
import com.besuikerd.autologistics.common.lib.dsl.vm._
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction._
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
  var program:String = ""

  virtualMachine.addNative("length", NativeFunctionLength.instance)
  virtualMachine.addNative("keys", NativeFunctionKeys.instance)
  virtualMachine.addNative("print", NativeFunctionPrint.instance)
  virtualMachine.addNative("println", NativeFunctionPrintln.instance)
  virtualMachine.addNative("say", nativeSay)

  object nativeSay extends NativeFunction {
    override def call(vm: VirtualMachine, args: JList[StackValue]): StackValue = {
      val players = TileVirtualMachine.this.getEntitiesWithinRange[EntityPlayer](10)
      for{
        arg <- args
        player <- players
      } {
        player.asInstanceOf[EntityPlayer].addChatComponentMessage(new ChatComponentText(arg.stringRepresentation))
      }
      NilValue.instance
    }
  }

  abstract override def readFromNBT(compound: NBTTagCompound): Unit = {
    super.readFromNBT(compound)
    this.program = compound.getString("program")
    readVMFromNBT(compound)
    println("read from nbt" + compound)
  }

  override def writeToNBT(compound: NBTTagCompound): Unit = {
    super.writeToNBT(compound)
    compound.setString("program", program)
    writeVMToNBT(compound)
  }

  def writeVMToNBT(compound:NBTTagCompound): Unit = {
    val output = ByteStreams.newDataOutput()
    virtualMachine.serialize(output)
    compound.setTag("vm", new NBTTagByteArray(output.toByteArray))
  }

  def readVMFromNBT(compound: NBTTagCompound): Unit = {
    val input = ByteStreams.newDataInput(compound.getByteArray("vm"))

    try {
      virtualMachine.deserialize(input);
    } catch{
      case e:IOException => {
        BLogger.error(s"could not deserialize VM at ($xCoord, $yCoord, $zCoord), resetting vm...")
        virtualMachine.reset()
      }
    }
  }



  def load(program:String): Unit ={
    AutoLogisticsParser.parse(AutoLogisticsParser.parser, program) match{
      case AutoLogisticsParser.Success(ast, _) => {
        val instructions = CodeGenerator.generate(ast)
        //println(ast)
        virtualMachine.load(instructions)
      }
      case AutoLogisticsParser.NoSuccess(error, _) => {
        println("Failed to load program: " + error)
      }
    }
  }
}

