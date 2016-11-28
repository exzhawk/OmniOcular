package me.exz.omniocular.proxy;

import codechicken.nei.guihook.GuiContainerManager;
import cpw.mods.fml.common.event.FMLInterModComms;
import me.exz.omniocular.OmniOcular;
import me.exz.omniocular.command.CommandEntityName;
import me.exz.omniocular.command.CommandItemName;
import me.exz.omniocular.handler.ConfigHandler;
import me.exz.omniocular.handler.TooltipHandler;
import me.exz.omniocular.util.LogHelper;
import net.minecraftforge.client.ClientCommandHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;

import java.net.URI;

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

    @Override
    public void startHttpServer() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Server server = new Server(23333);
                try {
                    URI uri = OmniOcular.class.getResource("/assets/omniocular/static/").toURI();
                    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
                    context.setContextPath("/");
                    context.setBaseResource(Resource.newResource(uri));
                    context.setWelcomeFiles(new String[]{"index.html"});
                    ServletHolder holderPwd = new ServletHolder("default", DefaultServlet.class);
                    context.addServlet(holderPwd, "/");
                    server.setHandler(context);
                    server.start();
                    server.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

    }
}
