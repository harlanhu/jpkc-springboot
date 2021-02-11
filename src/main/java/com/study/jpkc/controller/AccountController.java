package com.study.jpkc.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.study.jpkc.common.component.KaptchaComponent;
import com.study.jpkc.common.dto.LoginDto;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.User;
import com.study.jpkc.service.IUserService;
import com.study.jpkc.shiro.AccountProfile;
import com.study.jpkc.utils.JwtUtils;
import com.study.jpkc.utils.RedisUtils;
import com.study.jpkc.utils.RegexUtils;
import com.study.jpkc.utils.TimeUtils;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.time.LocalDateTime;


/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/20 0:02
 * @desc 登录相关
 */
@RestController
@Api("获取Token")
public class AccountController {

    private final IUserService userService;

    private final RedisUtils redisUtils;

    private final JwtUtils jwtUtils;

    private final KaptchaComponent kaptchaComponent;

    public static final String AUTHORIZATION = "Authorization";

    public AccountController(IUserService userService, JwtUtils jwtUtils, KaptchaComponent kaptchaComponent, RedisUtils redisUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.kaptchaComponent = kaptchaComponent;
        this.redisUtils = redisUtils;
    }

    /**
     * 登录接口
     * @param loginDto 登录实体
     * @param response resp
     * @return 响应结果
     */
    @RequiresGuest
    @PostMapping("/login")
    public Result login(@RequestBody @Validated LoginDto loginDto, HttpServletResponse response) {
        //验证码校验
        kaptchaComponent.validate(loginDto.getVerifyCode());
        //判断用户名类型是否满足要求
        if (!RegexUtils.isUsername(loginDto.getUsername())) {
            throw new IllegalArgumentException("用户名格式未满足要求");
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (RegexUtils.emailMatches(loginDto.getUsername())) {
            wrapper.eq("user_email", loginDto.getUsername());
        }
        if (RegexUtils.phoneMatches(loginDto.getUsername())) {
            wrapper.eq("user_phone", loginDto.getUsername());
        }
        if (RegexUtils.usernameMatches(loginDto.getUsername())) {
            wrapper.eq("username", loginDto.getUsername());
        }
        User user = userService.getOne(wrapper);
        Assert.notNull(user, "用户不存在");
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.getFailRes("密码错误!");
        }
        user.setUserLogin(LocalDateTime.now());
        userService.update(new UpdateWrapper<User>().eq("user_id", user.getUserId()).set("user_login", LocalDateTime.now()));
        String token = jwtUtils.generateToken(user.getUsername());
        AccountProfile accountProfile = new AccountProfile(user.getUserId(), user.getUsername(), user.getUserPhone(), user.getUserEmail(), user.getUserAvatar());
        redisUtils.set(token, accountProfile, TimeUtils.getTime(10,0,0,0));
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
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        SecurityUtils.getSubject().logout();
        String token = request.getHeader(AUTHORIZATION);
        redisUtils.del(token);
        return Result.getSuccessRes(null);
    }

    @RequiresGuest
    @RequestMapping("/tokenExpiry")
    public ResultSet tokenExpiry() {
        throw new ExpiredSessionException("Token已过期, 请重新登录");
    }

    /**
     * 角色测试接口
     * @return 返回结果
     */
    @RequiresRoles("/test")
    @GetMapping("roles")
    public Result testRoles() {
        return Result.getSuccessRes(true);
    }


    /**
     * 权限测试接口
     * @return 返回结果
     */
    @RequiresPermissions("/test")
    @GetMapping("permission")
    public Result testPermission() {
        return Result.getSuccessRes(true);
    }
}
