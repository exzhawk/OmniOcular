package me.exz.omniocular.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandReloadConfig extends CommandBase {
    @Override
    public String getCommandName() {
        return "oor";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        //TODO: if is singleplayer or player. isOp,return true;return false

        if (MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(sender)) {
            //TODO: not working maybe
            return true;
        }
        return false;
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/oor";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] array) {
        //config.loadConfig((EntityPlayer) sender);
        //TODO: send to all player config string
    }

}