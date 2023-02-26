package me.exz.omniocular.handler;

import me.exz.omniocular.proxy.ClientProxy;
import me.exz.omniocular.util.LogHelper;
import me.exz.omniocular.util.NBTHelper;
import me.exz.omniocular.util.WebSocketMsg;
import net.minecraft.nbt.NBTBase;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;


public class WebSocketHandler extends WebSocketAdapter {
    private static String lastBroadcastString = "";

    static void broadcast(NBTBase n) {
        WebSocketMsg msg = new WebSocketMsg(n, "nbt");
        String msgString = NBTHelper.NBT2json(msg);
        broadcast(msgString);
    }

    static void broadcast(String message) {
        if (!lastBroadcastString.equals(message)) {
            lastBroadcastString = message;
            for (WebSocketHandler webSocketClient : ClientProxy.webSocketClients) {
                if (webSocketClient.getSession().isOpen()) {
                    webSocketClient.sendWebSocketText(message);
                }
            }
        }
    }

    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {

    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);
        ClientProxy.webSocketClients.remove(this);
    }

    @Override
    public void onWebSocketConnect(Session sess) {
        super.onWebSocketConnect(sess);
        LogHelper.info("WebSocket Connect: {}" + sess.toString());
        ClientProxy.webSocketClients.add(this);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);
        LogHelper.warn("WebSocket Error " + cause);
    }

    @Override
    public void onWebSocketText(String message) {
        if (isConnected()) {
            LogHelper.info("Received message " + message);
        }
    }

    private void sendWebSocketText(String message) {
        getRemote().sendStringByFuture(message);
    }
}
