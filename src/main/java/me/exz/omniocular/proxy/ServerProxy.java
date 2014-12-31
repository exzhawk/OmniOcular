package me.exz.omniocular.proxy;

import me.exz.omniocular.handler.ConfigHandler;

@SuppressWarnings("UnusedDeclaration")
public class ServerProxy extends CommonProxy {

    @Override
    public void registerClientCommand() {

    }

    @Override
    public void registerWaila() {

    }

    @Override
    public void registerNEI() {

    }

    @Override
    public void prepareConfigFiles() {
        ConfigHandler.mergeConfig();
    }
}
