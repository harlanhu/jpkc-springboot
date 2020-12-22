package com.study.jpkc.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.study.jpkc.common.component.SmsComponent;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.User;
import com.study.jpkc.service.IUserService;
import com.study.jpkc.shiro.AccountProfile;
import com.study.jpkc.utils.RedisUtils;
import com.study.jpkc.utils.RegexUtils;
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
    private static final String ALREADY_EXISTED_USERNAME = "该用户名已被注册";
    private static final String ALREADY_EXISTED_EMAIL = "该邮箱已被注册";
    private static final String ALREADY_EXISTED_PHONE = "该手机已被注册";


    /**
     * 用户注册接口
     * @param user 用户信息
     * @return 返回结果
     */
    @RequiresGuest
    @PostMapping("register")
    public Result register(@RequestBody @Validated User user) {
        User userOfName = userService.getUserByUsername(user.getUsername());
        if (ObjectUtil.isNotEmpty(userOfName)) {
            return Result.getFailRes(ALREADY_EXISTED_USERNAME);
        }
        User userOfEmail = userService.getUserByEmail(user.getUserEmail());
        if (ObjectUtil.isNotEmpty(userOfEmail)) {
            return Result.getFailRes(ALREADY_EXISTED_EMAIL);
        }
        User userOfPhone = userService.getUserByPhone(user.getUserPhone());
        if (ObjectUtil.isNotEmpty(userOfPhone)) {
            return Result.getFailRes(ALREADY_EXISTED_PHONE);
        }
        if (userService.save(user)) {
            AccountProfile accountProfile = new AccountProfile();
            BeanUtil.copyProperties(user, accountProfile);
            return Result.getSuccessRes(accountProfile, SUCCESS_REGISTER_MESSAGE);
        }
        return Result.getFailRes(FAIL_REGISTER_MESSAGE);
    }

    @RequiresGuest
    @PostMapping("registerByPhone")
    public Result registerByPhone(@RequestBody String userPhone, @RequestBody String password, @RequestBody String verifyCode) {
        if (!RegexUtils.phoneMatches(userPhone)) {
            return Result.getFailRes(RegexUtils.INCORRECT_FORMAT_PHONE);
        }
        if (!RegexUtils.passwordMatches(password)) {
            return Result.getFailRes(RegexUtils.INCORRECT_FORMAT_PASSWORD);
        }
        if (!RegexUtils.verifyCodeMatches(verifyCode)) {
            return Result.getFailRes(RegexUtils.INCORRECT_FORMAT_VERIFY_CODE);
        }
        String code = (String) redisUtils.get(userPhone + "verifyCode");
        if (ObjectUtil.isEmpty(code)) {
            return Result.getFailRes(SmsComponent.EXPIRES_CODE);
        }
        if (code.equals(verifyCode)) {
            if (userService.saveUserByPhone(userPhone, password)) {
                return Result.getSuccessRes(null);
            }
            return Result.getFailRes(FAIL_REGISTER_MESSAGE);
        }
        return null;
    }

    @RequiresGuest
    @PostMapping("registerByEmail")
    public Result registerByEmail(@RequestBody String userEmail, @RequestBody String password) {
        if (!RegexUtils.emailMatches(userEmail)) {
            return Result.getFailRes(RegexUtils.INCORRECT_FORMAT_EMAIL);
        }
        if (!RegexUtils.passwordMatches(password)) {
            return Result.getFailRes(RegexUtils.INCORRECT_FORMAT_PASSWORD);
        }
        User user = userService.getUserByEmail(userEmail);
        if (ObjectUtil.isNotEmpty(user)) {
            return Result.getFailRes(ALREADY_EXISTED_EMAIL);
        }
        if (userService.saveUserByEmail(userEmail, password)) {
            return Result.getSuccessRes(ALREADY_EXISTED_EMAIL);
        }
        return null;
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
