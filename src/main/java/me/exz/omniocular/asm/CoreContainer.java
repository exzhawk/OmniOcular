package me.exz.omniocular.asm;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

import java.util.Arrays;

class CoreContainer extends DummyModContainer{
    public CoreContainer()
    {
        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = "OmniOcularCore";
        meta.name = "Omni Ocular Core";
        meta.version = "1.0";
        meta.authorList = Arrays.asList("Epix");
        meta.description = "A CoreMod to inject into Waila method";
        meta.url = "http://exz.me";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        bus.register(this);
        return true;
    }
}
