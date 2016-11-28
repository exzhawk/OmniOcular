package me.exz.omniocular.proxy;

import cpw.mods.fml.common.event.FMLInterModComms;
import me.exz.omniocular.handler.ConfigHandler;

@SuppressWarnings("UnusedDeclaration")
public class ServerProxy extends CommonProxy {

    @Override
    public void registerClientCommand() {

    }

    @Override
    public void registerWaila() {
        FMLInterModComms.sendMessage("Waila", "register", "me.exz.omniocular.handler.EntityHandler.callbackRegister");
        FMLInterModComms.sendMessage("Waila", "register", "me.exz.omniocular.handler.TileEntityHandler.callbackRegister");
    }

    @Override
    public void registerNEI() {

    }

    @Override
    public void prepareConfigFiles() {
        ConfigHandler.mergeConfig();
    }

    @Override
    public void startHttpServer() {

    }
}
