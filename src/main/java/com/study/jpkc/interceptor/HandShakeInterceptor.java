package com.study.jpkc.interceptor;

import com.study.jpkc.entity.User;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author Harlan
 * @Date 2021/4/1
 */
public class HandShakeInterceptor extends HttpSessionHandshakeInterceptor {

    private static final Set<User> ONLINE_USERS = new HashSet<>();
}
