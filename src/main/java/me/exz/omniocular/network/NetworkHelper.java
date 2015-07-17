package me.exz.omniocular.network;

import me.exz.omniocular.handler.ConfigHandler;
import me.exz.omniocular.util.LogHelper;
import net.minecraft.entity.player.EntityPlayerMP;

public class NetworkHelper {

    private static void sendString(String string, EntityPlayerMP player) {
        ConfigMessageHandler.network.sendTo(new ConfigMessage(string), player);
    }

    public static void sendConfigString(String string, EntityPlayerMP player) {
        sendString("__START__", player);
        int size = 10240;
        while (string.length() > size) {
            sendString(string.substring(0, size), player);
            string = string.substring(size);
        }
        sendString(string, player);
        sendString("__END__", player);
    }

    public static void recvConfigString(String string) {
        if (string.equals("__START__")) {
            ConfigHandler.mergedConfig = "";
        } else if (string.equals("__END__")) {
            LogHelper.info("received config: " + ConfigHandler.mergedConfig);
            ConfigHandler.parseConfigFiles();
        } else {
            ConfigHandler.mergedConfig += string;
        }
    }
}
