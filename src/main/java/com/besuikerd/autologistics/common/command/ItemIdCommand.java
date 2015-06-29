package com.besuikerd.autologistics.common.command;

import com.besuikerd.autologistics.common.lib.network.ClipboardMessageHandler;
import com.besuikerd.autologistics.common.lib.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class ItemIdCommand extends ModCommand{
    @Override
    public String getCommandName() {
        return "hand";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayer player = sender.getEntityWorld().getPlayerEntityByName(sender.getCommandSenderName());
        if(player != null && player instanceof EntityPlayerMP){
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            ItemStack heldItem = player.getHeldItem();
            if(heldItem != null){
                boolean useMeta = false;
                if(args.length > 0){
                    useMeta = true;
                }
                GameRegistry.UniqueIdentifier ui = GameRegistry.findUniqueIdentifierFor(heldItem.getItem());
                String itemId = "<" + ui.modId + ":" + ui.name + (useMeta ? ":" + heldItem.getItemDamage() + ">" : ">");

                PacketDispatcher.instance.sendTo(new ClipboardMessageHandler.ClipboardMessage(itemId), playerMP);
            }
        }
    }

}
