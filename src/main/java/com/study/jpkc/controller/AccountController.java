package com.study.jpkc.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.User;
import com.study.jpkc.service.IUserService;
import com.study.jpkc.shiro.AccountProfile;
import com.study.jpkc.utils.JwtUtils;
import com.study.jpkc.utils.RedisUtils;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/20 0:02
 * @desc 登录相关
 */
@RestController
@Api("获取Token")
public class AccountController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) {
        if (StringUtils.isBlank(username)) {
            return Result.getFailRes("用户名不能为空!");
        }
        if (StringUtils.isBlank(password)) {
            return Result.getFailRes("密码不能为空!");
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        Assert.notNull(user, "用户不存在");
        if (!user.getPassword().equals(SecureUtil.md5(password))) {
            System.out.println(SecureUtil.md5(password));
            System.out.println(user.getPassword());
            return Result.getFailRes("密码错误!");
        }
        String token = jwtUtils.generateToken(username);
        AccountProfile accountProfile = new AccountProfile(user.getUserId(), user.getUsername(), user.getUserPhone(), user.getUserEmail(), user.getUserAvatar());
        redisUtils.set(token, accountProfile, 60 * 60 * 24 * 3);
        response.setHeader("Authorization", token);
        response.setHeader("Access-control-Expose-Headers", "Authorization");
        return Result.getSuccessRes(MapUtil.builder()
        .put("userId", user.getUserId())
        .put("username", user.getUsername())
        .put("userPhone", user.getUserPhone())
        .put("userEmail", user.getUserEmail())
        .put("userAvatar", user.getUserAvatar())
        .map());
    }

    @RequiresAuthentication
    @GetMapping("logout")
    public Result logout(HttpServletRequest request) {
        SecurityUtils.getSubject().logout();
        String token = request.getHeader("Authorization");
        redisUtils.del(token);
        return Result.getSuccessRes(null);
    }
}
