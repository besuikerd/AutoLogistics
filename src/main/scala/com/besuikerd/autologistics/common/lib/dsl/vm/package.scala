package com.besuikerd.autologistics.common.lib.dsl

import java.util.{List => JList}
import com.besuikerd.autologistics.common.lib.dsl.vm.VirtualMachine
import com.besuikerd.autologistics.common.lib.dsl.vm.nativefunction.NativeFunction
import com.besuikerd.autologistics.common.lib.dsl.vm.stackvalue.StackValue

package object vm {
  type ScalaNativeFunction = (VirtualMachine, JList[StackValue]) => StackValue

  implicit def scalaFunctionToNativeFunction(f: ScalaNativeFunction):NativeFunction = new NativeFunction{
    override def call(vm: VirtualMachine, args: JList[StackValue]): StackValue = f(vm, args)
  }
}
