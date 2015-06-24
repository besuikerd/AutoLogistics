package com.besuikerd.autologistics.common.lib.dsl

import java.util
import java.util.List
import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.NativeFunction
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue

package object vm {
  implicit class ScalaNativeFunction(f: (VirtualMachine, List[StackValue]) => StackValue) extends NativeFunction{
    override def call(vm: VirtualMachine, args: util.List[StackValue]): StackValue = f(vm, args)
  }
}
