package com.besuikerd.autologistics.common.lib.network;

import com.besuikerd.autologistics.common.lib.util.ClipBoard;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class ClipboardMessageHandler extends ClientMessageHandler<ClipboardMessageHandler.ClipboardMessage, IMessage>{

    @Override
    public IMessage onClientMessage(EntityPlayer player, ClipboardMessage message, MessageContext ctx) {
        String chatMessage = message.getClipboard();
        if(message.getClipboard().length() > 100){
            chatMessage = chatMessage.substring(0, 100) + "...";
        }

        ClipBoard.set(message.getClipboard());

        player.addChatComponentMessage(new ChatComponentText("Added " + chatMessage + " to Clipboard"));
        return null;
    }

    public static class ClipboardMessage implements IMessage{
        private String clipboard;

        public ClipboardMessage(String msg) {
            this.clipboard = msg;
        }

        public String getClipboard() {
            return clipboard;
        }

        public ClipboardMessage(){
            this(null);
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            this.clipboard = ByteBufUtils.readUTF8String(buf);
        }

        @Override
        public void toBytes(ByteBuf buf) {
            ByteBufUtils.writeUTF8String(buf, clipboard);
        }
    }
}
