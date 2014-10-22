package me.exz.omniocular.proxy;

import cpw.mods.fml.common.event.FMLServerStartingEvent;
import me.exz.omniocular.command.CommandReloadConfig;

@SuppressWarnings("UnusedDeclaration")
public class ServerProxy extends CommonProxy {
    @Override
    public void registerClientCommand() {

    }

    @Override
    public void registerServerCommand(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandReloadConfig());
    }

    @Override
    public void registerWaila() {

    }

    @Override
    public void registerNEI() {

    }
}
