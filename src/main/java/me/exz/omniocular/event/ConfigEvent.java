package me.exz.omniocular.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import me.exz.omniocular.network.ConfigMessage;
import me.exz.omniocular.network.ConfigMessageHandler;
import me.exz.omniocular.util.LogHelper;
import net.minecraft.server.MinecraftServer;

public class ConfigEvent {
    @SubscribeEvent
    public void PlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        //LogHelper.info("Initializing config");

        //config.loadConfig(event.player);

        ConfigMessageHandler.network.sendTo(new ConfigMessage("toPlayer"), (net.minecraft.entity.player.EntityPlayerMP) event.player);
        //TODO: send config to (player, config string)

        LogHelper.info("PlayerLoggedInEvent " + event.player.worldObj.isRemote);
        MinecraftServer.getServer().isDedicatedServer();
        MinecraftServer.getServer().isSinglePlayer();
    }
}
