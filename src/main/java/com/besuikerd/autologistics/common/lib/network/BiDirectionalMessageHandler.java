package com.besuikerd.autologistics.common.lib.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import scala.tools.nsc.interactive.REPL;

public abstract class BiDirectionalMessageHandler<REQ extends IMessage, REPLY extends IMessage> extends SidedMessageHandler<REQ, REPLY> {
}
