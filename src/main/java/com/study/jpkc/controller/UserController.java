package com.study.jpkc.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.User;
import com.study.jpkc.service.IUserService;
import com.study.jpkc.shiro.AccountProfile;
import com.study.jpkc.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtils redisUtils;

    private static final String FAIL_REGISTER_MESSAGE = "注册失败，请稍后再试";
    private static final String SUCCESS_REGISTER_MESSAGE = "注册成功";

    /**
     * 用户注册接口
     * @param user 用户信息
     * @return 返回结果
     */
    @RequiresGuest
    @PostMapping("register")
    public Result register(@RequestBody @Validated User user) {
        User userOfName = userService.getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (ObjectUtil.isNotEmpty(userOfName)) {
            return Result.getFailRes("该用户名已被注册");
        }
        User userOfEmail = userService.getOne(new QueryWrapper<User>().eq("user_email", user.getUserEmail()));
        if (ObjectUtil.isNotEmpty(userOfEmail)) {
            return Result.getFailRes("该邮箱已被注册");
        }
        User userOfPhone = userService.getOne(new QueryWrapper<User>().eq("user_phone", user.getUserPhone()));
        if (ObjectUtil.isNotEmpty(userOfPhone)) {
            return Result.getFailRes("该手机已被注册");
        }
        if (userService.save(user)) {
            AccountProfile accountProfile = new AccountProfile();
            BeanUtil.copyProperties(user, accountProfile);
            return Result.getSuccessRes(accountProfile, SUCCESS_REGISTER_MESSAGE);
        }
        return Result.getFailRes(FAIL_REGISTER_MESSAGE);
    }

    /**
     * 用户邮箱激活接口
     * @param md5Code 激活码
     * @return 返回结果
     */
    @RequiresGuest
    @GetMapping("activate/{md5Code}")
    public Result activate(@PathVariable String md5Code) {
        Map<Object, Object> keyMap = redisUtils.getHash(md5Code);
        if (ObjectUtil.isEmpty(keyMap)) {
            return Result.getFailRes("激活链接已失效，请重新获取");
        }
        String key = (String) keyMap.get("key");
        String username = (String) keyMap.get("username");
        String userId = (String) keyMap.get("userId");
        String salt = (String) keyMap.get("salt");
        String enCode = DigestUtil.md5Hex(username + salt + userId);
        if (enCode.equals(md5Code + key)) {
            redisUtils.del(md5Code);
            return Result.getSuccessRes(username, "激活成功");
        }
        return Result.getFailRes("请点击正确的激活链接");
    }

}
