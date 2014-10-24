package me.exz.omniocular.proxy;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import me.exz.omniocular.handler.ConfigHandler;
import me.exz.omniocular.network.ConfigMessage;
import me.exz.omniocular.network.ConfigMessageHandler;

public abstract class CommonProxy implements IProxy {


    @Override
    public void registerNetwork() {
        ConfigMessageHandler.network.registerMessage(ConfigMessageHandler.class, ConfigMessage.class, 0, Side.CLIENT);

    }

    @Override
    public void initConfig(FMLPreInitializationEvent event) {
        ConfigHandler.minecraftConfigDirectory = event.getModConfigurationDirectory();
        ConfigHandler.initConfigFiles();
        ConfigHandler.mergeConfig();
    }

}
