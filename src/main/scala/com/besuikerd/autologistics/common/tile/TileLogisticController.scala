package com.besuikerd.autologistics.common.tile

import com.besuikerd.autologistics.AutoLogistics
import com.besuikerd.autologistics.common.tile.traits.{TileLogistic, TileVirtualMachine, TileCable}
import cpw.mods.fml.relauncher.Side

class TileLogisticController extends TileEntityMod
  with TileVirtualMachine
  with TileLogistic
  with TileCable
{
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
      |repeat = \n f -> {
      | i = 0
      | while(i < n){
      |   f()
      |   i = i + 1
      | }
      |}
      |
      |need = \n item to f -> {
      |  while(count(to@item) < n){
      |    f()
      |  }
      |}
      |
      |log = <minecraft:log>
      |planks = <minecraft:planks>
      |iron = <minecraft:iron_ingot>
      |gold = <minecraft:gold_ingot>
      |redstone = <minecraft:redstone>
      |cobble = <minecraft:cobblestone>
      |glass = <minecraft:glass>
      |flint = <minecraft:flint>
      |piston = <minecraft:piston>
      |buffer = ~(1, 1, 0)
      |output = ~(2, 1, 0)
      |
      |
      |
      |mkPlanks = \ -> <Chest> >> [[log]] >> buffer
      |
      |mkPiston = \ -> {
      |  need(3, planks, buffer, mkPlanks)
      |  <Chest> >> [
      |    [planks planks planks]
      |    [cobble iron cobble]
      |    [cobble redstone cobble]
      |  ] >> buffer
      |}
      |
      |copper = <ore:ingotCopper>
      |copperGear = <ore:gearCopper>
      |tin = <ore:ingotTin>
      |tinGear = <ore:gearTin>
      |
      |mkGear = \material -> \ -> <Chest> >> [
      |  [null material null]
      |  [material iron material]
      |  [null material null]
      |] >> buffer
      |
      |coil = <ThermalExpansion:material:1>
      |mkCoil = \ -> <Chest> >> [
      |  [null null redstone]
      |  [null gold null]
      |  [redstone null null]
      |] >> buffer
      |
      |frame = <ThermalExpansion:Frame>
      |mkFrame = \ -> {
      |  println("das")
      |  need(1, tinGear, buffer, mkGear(tin))
      |  <Chest> >> [
      |    [iron glass iron]
      |    [glass tinGear glass]
      |    [iron glass iron]
      |  ] >> buffer
      |}
      |
      |pulverizer = <thermalexpansion.Pulverizer>
      |mkPulverizer = \ -> {
      | need(1, piston, buffer, mkPiston)
      | need(2, copperGear, buffer, mkGear(copper))
      | need(1, coil, buffer, mkCoil)
      | need(1, frame, buffer, mkFrame)
      | <Chest> >> [
      |   [null piston null]
      |   [flint frame flint]
      |   [copperGear coil copperGear]
      | ] >> output
      |}
      |
      |mkPulverizer()
      |
    """.stripMargin


  val simpleProgram2 =
    """
      |item = <Th.*:Fr.*>
      |println(count(<.*>@[item]))
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