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
    |bucket = <minecraft:bucket>
    |cake = <minecraft:cake>
    |recipe = [
    | [milk milk milk]
    | [sugar egg sugar]
    | [wheat wheat wheat]
    |]
    |
    |
    |in = ~(-2, 0, 0)
    |out1 = ~(2, 0, 0)
    |out2 = ~(2, 1, 0)
    |in >> recipe >> [out1@[bucket] out2@[cake]]
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
       |in1 = ~(-2, 0, 0)
       |in2 = ~(-2, 1, 0)
       |out1 = ~(2, 0, 0)
       |out2 = ~(2, 1, 0)
       |item = <minecraft:log>
       |recipe = [[item]]
       |
       |while((in1 >> recipe >> in2).success){}
       |println("done")
    """.stripMargin


  val pulzerizer =
    """
      |println(count([
      |   <Chest>@[<minecraft:cobblestone>]
      |   <Chest>@[<minecraft:cobblestone>]
      |
      |   ]
      |   )
      |
      |   )
      |
    """.stripMargin

  load(pulzerizer)

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