package com.besuikerd.autologistics.common.lib.dsl.old.vm

trait Instruction{
  def execute(machine: OldVirtualMachine):Unit
}
