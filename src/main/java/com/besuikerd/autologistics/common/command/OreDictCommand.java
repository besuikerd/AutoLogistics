package com.besuikerd.autologistics.common.command;

import com.besuikerd.autologistics.common.lib.network.ClipboardMessageHandler;
import com.besuikerd.autologistics.common.lib.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictCommand extends ModCommand{
    @Override
    public String getCommandName() {
        return "ore";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        EntityPlayer player = sender.getEntityWorld().getPlayerEntityByName(sender.getCommandSenderName());
        if(player != null && player instanceof EntityPlayerMP){
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            ItemStack heldItem = player.getHeldItem();
            if(heldItem != null){
                int[] ids = OreDictionary.getOreIDs(heldItem);
                if(ids.length == 0){
                    player.addChatMessage(new ChatComponentText(heldItem.getDisplayName() + " does not have any OreDictionary entries"));
                } else{

                    StringBuilder builder = new StringBuilder("OreDictionary entries for " + heldItem.getDisplayName() + ": [");
                    for(int i = 0 ; i < ids.length ; i++){
                        String oreName = OreDictionary.getOreName(ids[i]);

                        if(i == 0){
                            String ore = "<ore:" + oreName + ">";
                            PacketDispatcher.instance.sendTo(new ClipboardMessageHandler.ClipboardMessage(ore), playerMP);

                        } else{
                            builder.append(", ");
                        }
                        builder.append(oreName);
                    }
                    builder.append("]");

                    player.addChatComponentMessage(new ChatComponentText(builder.toString()));
                }
            }
        }
    }
}
