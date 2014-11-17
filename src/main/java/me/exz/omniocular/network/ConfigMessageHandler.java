package me.exz.omniocular.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import me.exz.omniocular.reference.Reference;

public class ConfigMessageHandler implements IMessageHandler<ConfigMessage, IMessage> {
    public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    @Override
    public IMessage onMessage(ConfigMessage message, MessageContext ctx) {
        //LogHelper.info("Config Received: "+ message.text);
        NetworkHelper.recvConfigString(message.text);
        return null;
    }
}