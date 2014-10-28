package me.exz.omniocular.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;

public class CommandEntityName extends CommandBase {
    @Override
    public String getCommandName() {
        return "ooe";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/ooe";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] array) {
        EntityPlayer player = (EntityPlayer) sender;
        Minecraft minecraft = Minecraft.getMinecraft();
        MovingObjectPosition objectMouseOver = minecraft.objectMouseOver;
        if (objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            Class pointEntityClass = objectMouseOver.entityHit.getClass();
            if (EntityList.classToStringMapping.containsKey(pointEntityClass)) {
                player.addChatComponentMessage(new ChatComponentText(EntityList.getEntityString(objectMouseOver.entityHit)));
            }
        } else {
            player.addChatComponentMessage(new ChatComponentTranslation("wailanbt.info.NotPointing"));
            //TODO: update feedback 
        }
    }
}