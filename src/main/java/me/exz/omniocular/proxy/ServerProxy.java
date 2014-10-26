package me.exz.omniocular.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import me.exz.omniocular.command.CommandReloadConfig;
import me.exz.omniocular.event.ConfigEvent;

@SuppressWarnings("UnusedDeclaration")
public class ServerProxy extends CommonProxy {
    @Override
    public void registerEvent() {
        FMLCommonHandler.instance().bus().register(new ConfigEvent());
    }
    @Override
    public void registerClientCommand() {

    }

    @Override
    public void registerWaila() {

    }

    @Override
    public void registerNEI() {

    }
}
