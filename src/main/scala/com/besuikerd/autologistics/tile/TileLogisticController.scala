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

//  val simpleProgram =
//    """
//      |chestWood = ~(2, 0, 0)
//      |chestCharcoal = ~(2, 1, 0)
//      |furnace = <minecraft:furnace>
//      |charcoal = <minecraft:coal:1>
//      |log = <ore:logWood>
//      |while(true) {
//      |  chestWood >> furnace@[up]
//      |  chestCharcoal >> furnace@[north, 1]
//      |  furnace@[down, charcoal] >> chestCharcoal
//      |}
//    """.stripMargin

  val simpleProgram =
    """
      |fst = ~(-2, 0, 0)
      |snd = ~(2, 0, 0)
      |while(true){
      |  fst@[32] >> snd
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