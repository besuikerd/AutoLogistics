package com.besuikerd.autologistics.common.tile

import com.besuikerd.autologistics.AutoLogistics
import com.besuikerd.autologistics.common.tile.traits.{TileLogistic, TileVirtualMachine, TileCable}
import cpw.mods.fml.relauncher.Side

class TileLogisticController extends TileEntityMod
  with TileVirtualMachine
  with TileLogistic
  with TileCable
{
  val simpleProgram =
    """
      |chestWood = ~(2, 0, 0)
      |chestCharcoal = ~(2, 1, 0)
      |furnace = <minecraft:furnace>
      |charcoal = <minecraft:coal:1>
      |log = <ore:logWood>
      |while(true) {
      |  chestWood@[log, 256] >> furnace@[up]
      |  chestCharcoal >> furnace@[north, 1]
      |  furnace@[down, charcoal] >> chestCharcoal
      |}
    """.stripMargin

  val simpleProgram2 =
    """
      |planks = <minecraft:planks>
      |input = ~(-2, 0, 0)
      |output = ~(2, 0, 0)
      |input >> output
      |
    """.stripMargin


  val craft = """
    |wheat = <minecraft:wheat>
    |sugar = <minecraft:sugar>
    |egg = <minecraft:egg>
    |milk = <minecraft:milk_bucket>
    |recipe = [
    | [milk milk milk]
    | [sugar egg sugar]
    | [wheat wheat wheat]
    |]
    |in = ~(-2, 0, 0)
    |out = ~(2, 0, 0)
    |in >> recipe >> out
  """.stripMargin

  val count =
    """
      |i = 0
      |while(true){
      |  say("hello world!");
      |}
    """.stripMargin

  load(count)

  override def updateEntity(): Unit = {
    if(AutoLogistics.proxy.getSideOfThread == Side.SERVER) {
      if (!virtualMachine.isTerminated) {
        try{
          virtualMachine.run(5)
        } catch{
          case e:Exception => {
            e.printStackTrace()
          }
        }
      } else if (virtualMachine.isErrorState) {
        println(virtualMachine.getErrorMessage)
      }
    }
  }
}