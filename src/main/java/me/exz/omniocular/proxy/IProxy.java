package me.exz.omniocular.proxy;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public interface IProxy {
    void registerEvent();

    void registerClientCommand();

    void registerServerCommand(FMLServerStartingEvent event);

    void registerWaila();

    void registerNEI();

    void registerNetwork();

    void initConfig(FMLPreInitializationEvent event);

    void prepareConfigFiles();

    void startHttpServer();
}
