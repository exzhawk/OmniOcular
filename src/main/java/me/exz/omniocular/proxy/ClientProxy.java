package me.exz.omniocular.proxy;

import codechicken.nei.guihook.GuiContainerManager;
import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraftforge.client.ClientCommandHandler;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerClientCommand() {
        ClientCommandHandler.instance.registerCommand(new CommandName());
        ClientCommandHandler.instance.registerCommand(new CommandEntity());
    }

    @Override
    public void registerWaila() {
        FMLInterModComms.sendMessage("Waila", "register", "me.exz.wailanbt.handler.BlockHandler.callbackRegister");
        FMLInterModComms.sendMessage("Waila", "register", "me.exz.wailanbt.handler.EntityHandler.callbackRegister");
    }

    @Override
    public void registerNEI() {
        GuiContainerManager.addTooltipHandler(new TooltipHandler());
    }
}
