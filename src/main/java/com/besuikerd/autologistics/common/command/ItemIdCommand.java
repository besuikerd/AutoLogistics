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

public class ItemIdCommand implements ICommand{
    @Override
    public String getCommandName() {
        return "hand";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "besu hand";
    }

    @Override
    public List<String> getCommandAliases() {
        return null;
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
                System.out.println("dfs");
                GameRegistry.UniqueIdentifier ui = GameRegistry.findUniqueIdentifierFor(heldItem.getItem());
                String itemId = "<" + ui.modId + ":" + ui.name + (useMeta ? ":" + heldItem.getItemDamage() + ">" : ">");

                PacketDispatcher.instance.sendTo(new ClipboardMessageHandler.ClipboardMessage(itemId), playerMP);
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
