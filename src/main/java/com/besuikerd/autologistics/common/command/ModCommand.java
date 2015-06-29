package com.besuikerd.autologistics.common.command;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

import java.util.Collections;
import java.util.List;

public abstract class ModCommand implements ICommand{
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public abstract void processCommand(ICommandSender sender, String[] args);

    @Override
    public List getCommandAliases() {
        return Collections.EMPTY_LIST;
    }

    public int compareTo(Object o) {
        return 0;
    }
}
