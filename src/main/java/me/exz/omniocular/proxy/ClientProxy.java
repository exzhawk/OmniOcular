package me.exz.omniocular.proxy;

import codechicken.nei.guihook.GuiContainerManager;
import cpw.mods.fml.common.event.FMLInterModComms;
import me.exz.omniocular.command.CommandEntityName;
import me.exz.omniocular.command.CommandItemName;
import me.exz.omniocular.handler.ConfigHandler;
import me.exz.omniocular.handler.TooltipHandler;
import me.exz.omniocular.util.LogHelper;
import net.minecraftforge.client.ClientCommandHandler;

@SuppressWarnings("UnusedDeclaration")
public class ClientProxy extends CommonProxy {
    @Override
    public void registerClientCommand() {
        ClientCommandHandler.instance.registerCommand(new CommandItemName());
        ClientCommandHandler.instance.registerCommand(new CommandEntityName());
    }


    @Override
    public void registerWaila() {
        FMLInterModComms.sendMessage("Waila", "register", "me.exz.omniocular.handler.EntityHandler.callbackRegister");
        FMLInterModComms.sendMessage("Waila", "register", "me.exz.omniocular.handler.TileEntityHandler.callbackRegister");
    }

    @Override
    public void registerNEI() {
        GuiContainerManager.addTooltipHandler(new TooltipHandler());
    }

    @Override
    public void prepareConfigFiles() {
        try {
            ConfigHandler.releasePreConfigFiles();
        } catch (Exception e) {
            LogHelper.error("Can't release pre-config files");
            e.printStackTrace();
        }
        ConfigHandler.mergeConfig();
    }
}
