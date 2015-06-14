package com.besuikerd.autologistics.common.lib.network

import net.minecraftforge.fml.common.network.simpleimpl.IMessage

trait BiDirectionalMessageHandler[REQ <: IMessage, REPLY <: IMessage] extends SidedMessageHandler[REQ, REPLY]{

}
