package me.exz.omniocular.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import me.exz.omniocular.network.ConfigMessage;
import me.exz.omniocular.network.ConfigMessageHandler;
import me.exz.omniocular.util.LogHelper;

public class ConfigEvent {
    @SuppressWarnings("UnusedDeclaration")
    @SubscribeEvent
    public void clientLoggedIn(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        LogHelper.info("ClientConnectedToServerEvent");
    }

    @SubscribeEvent
    public void PlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        //LogHelper.info("Initializing config");

        //config.loadConfig(event.player);

        ConfigMessageHandler.network.sendTo(new ConfigMessage("toPlayer"), (net.minecraft.entity.player.EntityPlayerMP) event.player);

        LogHelper.info("PlayerLoggedInEvent " + event.player.worldObj.isRemote);
    }
}
