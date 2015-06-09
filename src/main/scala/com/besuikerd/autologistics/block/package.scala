package com.besuikerd.autologistics

import net.minecraft.util.{EnumFacing, BlockPos}

package object block {

  implicit class BlockPosExtensions(val pos:BlockPos) extends AnyVal{
    def sides: Iterable[BlockPos] = EnumFacing.values().map(pos.offset)
  }
}
