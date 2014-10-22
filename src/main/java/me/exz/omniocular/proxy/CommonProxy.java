package me.exz.omniocular.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import me.exz.omniocular.event.ConfigEvent;
import me.exz.omniocular.network.ConfigMessage;
import me.exz.omniocular.network.ConfigMessageHandler;
import net.minecraftforge.common.MinecraftForge;

public abstract class CommonProxy implements IProxy {
    @Override
    public void registerEvent() {
        MinecraftForge.EVENT_BUS.register(new ConfigEvent());
        FMLCommonHandler.instance().bus().register(new ConfigEvent());
    }

    @Override
    public void registerNetwork() {
        ConfigMessageHandler.network.registerMessage(ConfigMessageHandler.class, ConfigMessage.class, 0, Side.CLIENT);

    }
    @Override
    public void initConfigFiles(){
        //TODO: initialize config files. extract all pre-config files. maybe validate all configs
    }

}
