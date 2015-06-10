package com.besuikerd.autologistics.tile

import com.besuikerd.autologistics.tile.traits.{TileLogistic, TileVirtualMachine, TileCable}
import net.minecraft.inventory.IInventory
import net.minecraft.server.gui.IUpdatePlayerListBox
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.fml.common.registry.GameRegistry

class TileLogisticController extends TileEntityMod
  with TileVirtualMachine
  with TileLogistic
  with TileCable
  with IUpdatePlayerListBox
{

  val temp_program =
    """
      |charcoal = <minecraft:charcoal>
      |chest = <minecraft:chest>
      |wood = <minecraft:oak_wood>
      |tchest = <minecraft:trapped_chest>
      |furnace = <minecraft:furnace>
      |
      |while(true){
      |  chest[wood] >> furnace[top]
      |  tchest >> furnace[bottom, charcoal, 10]
      |  furnace[charcoal] >> chest
      |}
    """.stripMargin

  val simpleProgram =
    """
      |chest = <minecraft:chest>
      |tchest = <minecraft:trapped_chest>
      |furnace = <minecraft:furnace>
      |charcoal = <minecraft:coal:1>
      |log = <minecraft:log>
      |while(true) {
      | chest@[charcoal] >> furnace@[north]
      | chest@[log] >> furnace@[up]
      | furnace@[down] >> chest
      |
      |
      | chest >> tchest@[<minecraft:wool>]
      |}
    """.stripMargin

  load(simpleProgram)

  override def update(): Unit = {
    if(!virtualMachine.isTerminated()){
      virtualMachine.run(5)
    } else if(virtualMachine.isErrorState()){
      println(virtualMachine.instructions.top)
    }

  }
}