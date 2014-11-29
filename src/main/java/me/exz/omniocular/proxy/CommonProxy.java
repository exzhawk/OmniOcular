package me.exz.omniocular.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import me.exz.omniocular.command.CommandReloadConfig;
import me.exz.omniocular.event.ConfigEvent;
import me.exz.omniocular.handler.ConfigHandler;
import me.exz.omniocular.network.ConfigMessage;
import me.exz.omniocular.network.ConfigMessageHandler;
import me.exz.omniocular.util.LogHelper;

public abstract class CommonProxy implements IProxy {
    @Override
    public void registerServerCommand(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandReloadConfig());
    }

    @Override
    public void registerEvent() {
        FMLCommonHandler.instance().bus().register(new ConfigEvent());
    }

    @Override
    public void registerNetwork() {
        ConfigMessageHandler.network.registerMessage(ConfigMessageHandler.class, ConfigMessage.class, 0, Side.CLIENT);

    }

    @Override
    public void initConfig(FMLPreInitializationEvent event) {
        ConfigHandler.minecraftConfigDirectory = event.getModConfigurationDirectory();
        ConfigHandler.initConfigFiles();
        //JSHandler.initEngine();
    }

    @Override
    public void prepareConfigFiles() {
        try {
            ConfigHandler.releasePreConfigFiles();
        } catch (Exception e) {
            LogHelper.error("Can't release pre-config files");
        }
        ConfigHandler.mergeConfig();
    }
}
