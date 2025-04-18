package org.fentanylsolutions.cmotd.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

import org.fentanylsolutions.cmotd.Config;
import org.fentanylsolutions.cmotd.CustomMOTD;
import org.fentanylsolutions.cmotd.ModCache;
import org.fentanylsolutions.cmotd.util.Util;

public class CommandReload implements ICommand {

    @Override
    public String getCommandName() {
        return "custommotd_reload";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/custommotd_reload";
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<>();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        sender.addChatMessage(new ChatComponentText("Custom MOTD configuration reloaded"));
        Config.synchronizeConfiguration(CustomMOTD.configFile);
        ModCache.cacheMOTDListFile();
        ModCache.cachePlayerListFile();
        CustomMOTD.LOG.info(sender.getCommandSenderName() + " issued custommotd_reload command");
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return !(sender instanceof EntityPlayerMP) || Util.isOp((EntityPlayerMP) sender);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
