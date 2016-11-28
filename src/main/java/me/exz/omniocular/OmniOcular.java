package me.exz.omniocular;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import me.exz.omniocular.proxy.IProxy;
import me.exz.omniocular.reference.Reference;

import java.util.Map;

@SuppressWarnings({"UnusedParameters", "UnusedDeclaration"})
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:Waila;required-after:NotEnoughItems")

public class OmniOcular {
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.initConfig(event);
        proxy.registerNetwork();
        proxy.startHttpServer();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerWaila();
        proxy.registerClientCommand();
        proxy.registerEvent();
        proxy.prepareConfigFiles();
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
    @NetworkCheckHandler
    public static boolean check(Map<String,String> remote,Side side){
        return !(side == Side.SERVER && !remote.isEmpty() && !remote.containsKey(Reference.MOD_ID));
    }


}
