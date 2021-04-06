package com.study.jpkc.server;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Harlan
 * @Date 2021/4/6
 */
@ServerEndpoint("/message/{userId}")
@Component
@Slf4j
public class WebSocketServer {

    private static int onlineCount = 0;

    private static final ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<>();

    private Session session;

    private String userId = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        webSocketMap.put(userId, session);
        addOnlineCount();
        log.info("======== " + userId +" 建立了Socket连接========");
    }

    @SneakyThrows
    @OnMessage
    public void onMessage(String message, Session session) {
        sendMessage(message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        subOnlineCount();
        log.error("WebSocket连接异常:" + error.getMessage());
    }

    @OnClose
    public void onClose() {
        subOnlineCount();
        log.info("======== " + userId +" 断开了Socket连接========");
    }

    public void sendMessage(String message) throws IOException {
        if ("$test".equals(message)) {
            this.session.getBasicRemote().sendText(message);
        }
        Enumeration<String> keys = webSocketMap.keys();
        while (keys.hasMoreElements()) {
            String key =  keys.nextElement();
            if (!key.equals(this.userId)) {
                if (webSocketMap.get(key) == null) {
                    webSocketMap.remove(key);
                } else {
                    Session sessionValue = webSocketMap.get(key);
                    if (sessionValue.isOpen()) {
                        sessionValue.getBasicRemote().sendText(message);
                    } else {
                        sessionValue.close();
                        webSocketMap.remove(key);
                    }
                }
            }
        }
    }

    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        if (!StringUtils.isEmpty(userId) && webSocketMap.containsKey(userId)) {
            webSocketMap.get(userId).getBasicRemote().sendText(message);
        } else {
            log.info("======== 用户 " + userId + " 已离线========");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount ++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount --;
    }
}
