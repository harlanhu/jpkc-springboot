package com.study.jpkc.server;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.study.jpkc.common.dto.MessageDto;
import com.study.jpkc.entity.User;
import com.study.jpkc.service.ILiveCourseService;
import com.study.jpkc.service.IUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Harlan
 * @Date 2021/4/6
 */
@ServerEndpoint("/message/{liveId}/{userId}")
@Component
@Slf4j
public class WebSocketServer {

    private static int onlineCount = 0;

    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, Session>> webSocketMap = new ConcurrentHashMap<>();

    private static IUserService userService;

    private static ILiveCourseService lCourseService;

    private Session session;

    private String userId = "";

    private User user;

    private String liveId = "";

    @Autowired
    public void setUserService(IUserService userService) {
        WebSocketServer.userService = userService;
    }

    @Autowired
    public void setlCourseService(ILiveCourseService lCourseService) {
        WebSocketServer.lCourseService = lCourseService;
    }

    @SneakyThrows
    @OnOpen
    public void onOpen(Session session, @PathParam("liveId") String liveId, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        this.liveId = liveId;
        this.user = userService.getById(userId);
        lCourseService.addViews(liveId);
        if (ObjectUtil.isNull(webSocketMap.get(liveId))) {
            webSocketMap.put(liveId, new ConcurrentHashMap<>(10));
        }
        webSocketMap.get(liveId).put(userId, session);
        log.info("======== 用户：" + userId + " 进入了直播间： " + liveId + " ========");
        sendMessage("进入了直播间");
        addOnlineCount();
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
        MessageDto messageDto = new MessageDto(user, message, LocalDateTime.now());
        ConcurrentHashMap<String, Session> sessionMap = webSocketMap.get(liveId);
        if (ObjectUtil.isNull(sessionMap)) {
            webSocketMap.remove(liveId);
        } else {
            log.info("用户：" + userId + " 在直播间：" + liveId + " 发送弹幕：" + message);
            Enumeration<String> userIds = sessionMap.keys();
            while (userIds.hasMoreElements()) {
                String realUserId = userIds.nextElement();
                if (!realUserId.equals(this.userId)) {
                    if (ObjectUtil.isNull(sessionMap.get(userId))) {
                        sessionMap.remove(userId);
                    } else {
                        Session realSession = sessionMap.get(userId);
                        if (realSession.isOpen()) {
                            realSession.getBasicRemote().sendText(JSON.toJSONString(messageDto));
                        } else {
                            realSession.close();
                            sessionMap.remove(userId);
                        }
                    }
                }
            }
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
