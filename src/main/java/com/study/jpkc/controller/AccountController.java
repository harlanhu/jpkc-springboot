package com.study.jpkc.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.kaptcha.Kaptcha;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.study.jpkc.common.dto.LoginDto;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.User;
import com.study.jpkc.service.IUserService;
import com.study.jpkc.shiro.AccountProfile;
import com.study.jpkc.utils.JwtUtils;
import com.study.jpkc.utils.RedisUtils;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

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

    @Autowired
    private Kaptcha kaptcha;

    public static final String AUTHORIZATION = "Authorization";

    /**
     * 登录接口
     * @param loginDto 登录实体
     * @param response resp
     * @return 响应结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody @Validated LoginDto loginDto, HttpServletResponse response) {
        //判断用户名类型是否满足要求
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        boolean isUsername = false;
        if (loginDto.getUsername().matches(LoginDto.EMAIL_REGEX)) {
            wrapper.eq("user_email", loginDto.getUsername());
            isUsername = true;
        }
        if (loginDto.getUsername().matches(LoginDto.PHONE_REGEX)) {
            wrapper.eq("user_phone", loginDto.getUsername());
            isUsername = true;
        }
        if (loginDto.getUsername().matches(LoginDto.USERNAME_REGEX)) {
            wrapper.eq("username", loginDto.getUsername());
            isUsername = true;
        }
        User user;
        if (isUsername) {
            user = userService.getOne(wrapper);
        }else {
            throw new IllegalArgumentException("用户名格式未满足要求");
        }
        Assert.notNull(user, "用户不存在");
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.getFailRes("密码错误!");
        }
        user.setUserLogin(LocalDateTime.now());
        userService.update(new UpdateWrapper<User>().eq("user_id", user.getUserId()).set("user_login", LocalDateTime.now()));
        String token = jwtUtils.generateToken(user.getUsername());
        AccountProfile accountProfile = new AccountProfile(user.getUserId(), user.getUsername(), user.getUserPhone(), user.getUserEmail(), user.getUserAvatar());
        redisUtils.set(token, accountProfile, 60 * 60 * 24 * 3);
        response.setHeader(AUTHORIZATION, token);
        response.setHeader("Access-control-Expose-Headers", AUTHORIZATION);
        return Result.getSuccessRes(MapUtil.builder()
        .put("userId", user.getUserId())
        .put("username", user.getUsername())
        .put("userPhone", user.getUserPhone())
        .put("userEmail", user.getUserEmail())
        .put("userAvatar", user.getUserAvatar())
        .map());
    }

    /**
     * 登出接口
     * @param request req
     * @return 响应结果
     */
    @RequiresAuthentication
    @GetMapping("logout")
    public Result logout(HttpServletRequest request) {
        SecurityUtils.getSubject().logout();
        String token = request.getHeader(AUTHORIZATION);
        redisUtils.del(token);
        return Result.getSuccessRes(null);
    }

    /**
     * 角色测试接口
     * @return 返回结果
     */
    @RequiresRoles("test")
    @GetMapping("roles")
    public Result testRoles() {
        return Result.getSuccessRes(true);
    }


    /**
     * 权限测试接口
     * @return 返回结果
     */
    @RequiresPermissions("test")
    @GetMapping("permission")
    public Result testPermission() {
        return Result.getSuccessRes(true);
    }
}
