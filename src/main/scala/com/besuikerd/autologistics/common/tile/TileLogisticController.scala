package com.besuikerd.autologistics.common.tile

import com.besuikerd.autologistics.AutoLogistics
import com.besuikerd.autologistics.common.tile.traits.{TileLogistic, TileVirtualMachine, TileCable}
import cpw.mods.fml.relauncher.Side

class TileLogisticController extends TileEntityMod
  with TileVirtualMachine
  with TileLogistic
  with TileCable
{
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


  val simpleProgram =
    """
      |chestWood = ~(2, 0, 0)
      |chestCharcoal = ~(2, 1, 0)
      |furnace = <minecraft:furnace>
      |charcoal = <minecraft:coal:1>
      |log = <minecraft:log:0>
      |while(true) {
      |  chestWood@[log] >> furnace@[up]
      |  chestCharcoal >> furnace@[north, 1]
      |  furnace@[down, charcoal] >> chestCharcoal
      |}
    """.stripMargin

  val ore =
    """
      |furnace = <thermalexpansion.Furnace>
      |pulverizer = <thermalexpansion.Pulverizer>
      |input = ~(2, 0, 0)
      |buffer = ~(2, 1, 0)
      |output = <thermalexpansion.Cache>
      |while(true){
      | input >> pulverizer@[up]
      | pulverizer@[down west] >> buffer
      | buffer >> furnace@[up]
      | furnace@[west] >> output
      |}
    """.stripMargin

  val count =
    """
       |input = <Chest>
       |output = <Dropper>
       |item = <minecraft:iron_pickaxe:1>
       |recipe = [[item item]]
       |input >> recipe >> input
       |
       |
    """.stripMargin

  load(count)

  override def updateEntity(): Unit = {
    if(AutoLogistics.proxy.getSideOfThread == Side.SERVER) {
      if (!virtualMachine.isTerminated) {
        try{
          virtualMachine.run(50)
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