package me.exz.omniocular.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

public abstract class CommonProxy implements IProxy {
    @Override
    public void registerEvent() {
        MinecraftForge.EVENT_BUS.register(new ConfigEvent());
        FMLCommonHandler.instance().bus().register(new ConfigEvent());
    }
}
