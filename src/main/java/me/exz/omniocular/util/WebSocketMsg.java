package me.exz.omniocular.util;

import java.sql.Timestamp;

public class WebSocketMsg {
    private final Object data;
    private final String type;
    private final long timestamp;

    public WebSocketMsg(Object data, String type) {
        this.data = data;
        this.type = type;
        this.timestamp = (new Timestamp(System.currentTimeMillis())).getTime();

    }


}
