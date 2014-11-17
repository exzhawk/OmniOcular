package me.exz.omniocular.proxy;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public interface IProxy {
    public abstract void registerEvent();

    public abstract void registerClientCommand();

    public abstract void registerServerCommand(FMLServerStartingEvent event);

    public abstract void registerWaila();

    public abstract void registerNEI();

    public abstract void registerNetwork();

    public abstract void initConfig(FMLPreInitializationEvent event);

    public abstract void releasePreConfigFiles();
}
