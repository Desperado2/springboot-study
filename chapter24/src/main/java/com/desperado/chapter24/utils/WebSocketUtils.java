package com.desperado.chapter24.utils;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工具类
 */
public class WebSocketUtils {

    /**
     * 模拟存储 websocket session 使用
     */
    public static final Map<String, Session> LIVING_SESSIONS_CACHE = new ConcurrentHashMap<>();

    public static void sendMessageAll(String message){
        LIVING_SESSIONS_CACHE.forEach((sessionId,session)->sendMessage(session,message));
    }

    /**
     * 方式消息给指定用户
     * @param session  用户session
     * @param message  消息
     */
    public static void sendMessage(Session session, String message){
        if(session == null){
            return;
        }
        final  RemoteEndpoint.Basic remote = session.getBasicRemote();
        if (remote == null){
            return;
        }
        try {
            remote.sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
