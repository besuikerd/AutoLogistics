package com.besuikerd.autologistics.tile

class TileLogisticController extends TileEntityMod
  with TileVirtualMachine
{

  val temp_program =
    """
      |coal = <minecraft:coal>
      |chest = <mnecraft:chest>
      |furnace = <minecraft:furnace>
      |
      |while(true){
      |  chest >> furnace[north, coal, amount = 5]
      |  chest[<minecraft:wood>, 1]
      |  furnace[<minecraft:charcoal>] >> chest
      |}
    """.stripMargin


}