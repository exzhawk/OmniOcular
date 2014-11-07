package me.exz.omniocular.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import me.exz.omniocular.handler.ConfigHandler;
import me.exz.omniocular.network.ConfigMessage;
import me.exz.omniocular.network.ConfigMessageHandler;

public class ConfigEvent {
    @SubscribeEvent
    public void PlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        ConfigMessageHandler.network.sendTo(new ConfigMessage(ConfigHandler.mergedConfig), (net.minecraft.entity.player.EntityPlayerMP) event.player);
//        LogHelper.info("PlayerLoggedInEvent");
//        MinecraftServer.getServer().isDedicatedServer();
//        MinecraftServer.getServer().isSinglePlayer();
    }
}
