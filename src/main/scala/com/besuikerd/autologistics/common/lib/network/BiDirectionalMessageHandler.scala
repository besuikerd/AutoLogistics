package com.besuikerd.autologistics.common.lib.network

import cpw.mods.fml.common.network.simpleimpl.IMessage

trait BiDirectionalMessageHandler[REQ <: IMessage, REPLY <: IMessage] extends SidedMessageHandler[REQ, REPLY]{

}
