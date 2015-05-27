package com.besuikerd.autologistics.tile

import com.besuikerd.autologistics.lib.dsl.parser.DSLParser
import com.besuikerd.autologistics.lib.dsl.vm.VirtualMachine

trait TileVirtualMachine extends TileEntityMod{
  val virtualMachine = new VirtualMachine
}
