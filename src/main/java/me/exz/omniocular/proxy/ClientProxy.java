package me.exz.omniocular.proxy;

import codechicken.nei.guihook.GuiContainerManager;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import me.exz.omniocular.command.CommandEntityName;
import me.exz.omniocular.command.CommandItemName;
import me.exz.omniocular.handler.TooltipHandler;
import net.minecraftforge.client.ClientCommandHandler;

@SuppressWarnings("UnusedDeclaration")
public class ClientProxy extends CommonProxy {
    @Override
    public void registerEvent() {

    }

    @Override
    public void registerClientCommand() {
        ClientCommandHandler.instance.registerCommand(new CommandItemName());
        ClientCommandHandler.instance.registerCommand(new CommandEntityName());
    }

    @Override
    public void registerServerCommand(FMLServerStartingEvent event) {

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
