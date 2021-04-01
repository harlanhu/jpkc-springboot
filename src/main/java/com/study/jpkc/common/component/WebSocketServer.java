package com.study.jpkc.common.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.study.jpkc.shiro.AccountProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author isharlan.hu@gmail.com
 * @date 2021/3/13 17:23
 * @desc WebSocket组件
 */
@Component
@Slf4j
@ServerEndpoint("/jpkc/{token}")
public class WebSocketServer {

    private static int onlineCount = 0;
    private static ConcurrentHashMap<String, WebSocketServer> serverMap = new ConcurrentHashMap<>();
    private Session session;
    private String token = "";

    @OnOpen
    public void onOpen(@PathParam("token")String token, Session session) {
        this.session = session;
        this.token = token;
        if (serverMap.containsKey(token)) {
            serverMap.remove(token);
            serverMap.put(token, this);
        } else {
            serverMap.put(token, this);
            addOnlineCount();
        }
    }

    @OnClose
    public void onClose() {
        if (serverMap.containsKey(token)) {
            serverMap.remove(token);
            subOnlineCount();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if (StringUtils.isNotBlank(message)) {
            JSONObject jsonObject = JSON.parseObject(message);
            AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
            jsonObject.put("from", account);
            String to = jsonObject.getString("to");
            if (StringUtils.isNotBlank(to) && serverMap.containsKey(to)) {
                try {
                    serverMap.get(to).sendInfo(jsonObject.toJSONString());
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            } else {
                log.error("当前用户已离线");
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error(error.getMessage());
    }

    public void sendInfo(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static void sendMessage(@PathParam("token") String token, String message) throws IOException {
        if (StringUtils.isNotBlank(token) && serverMap.containsKey(token)) {
            serverMap.get(token).sendInfo(message);
        } else {
            log.error("当前用户已离线");
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
