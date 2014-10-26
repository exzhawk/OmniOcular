package me.exz.omniocular;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import me.exz.omniocular.proxy.IProxy;
import me.exz.omniocular.reference.Reference;
import me.exz.omniocular.util.LogHelper;

@SuppressWarnings({"UnusedParameters", "UnusedDeclaration"})
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:Waila;required-after:NotEnoughItems")

public class OmniOcular {
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.initConfig(event);
        proxy.registerNetwork();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerWaila();
        proxy.registerEvent();
        proxy.registerClientCommand();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.registerNEI();
    }

    @Mod.EventHandler
    void onServerStart(FMLServerStartingEvent event) {
//        LogHelper.info("FMLServerStartingEvent");
        proxy.registerServerCommand(event);
    }


}
