package com.study.jpkc.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.study.jpkc.common.component.KaptchaComponent;
import com.study.jpkc.common.component.SmsComponent;
import com.study.jpkc.common.dto.EmailRegisterDto;
import com.study.jpkc.common.dto.PhoneRegisterDto;
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

    @Autowired
    private KaptchaComponent kaptchaComponent;

    @Autowired
    private SmsComponent smsComponent;

    private static final Integer EMAIL_ACTIVATE = 2;
    private static final String FAIL_REGISTER_MESSAGE = "注册失败，请稍后再试";
    private static final String SUCCESS_REGISTER_MESSAGE = "注册成功";
    private static final String ALREADY_EXISTED_USERNAME = "该用户名已被注册";
    private static final String ALREADY_EXISTED_EMAIL = "该邮箱已被注册";
    private static final String ALREADY_EXISTED_PHONE = "该手机已被注册";
    private static final String ERROR_VERIFY_CODE = "验证码错误";


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

    /**
     * 用户通过手机注册接口
     * @param registerDto 注册信息封装
     * @return 返回信息
     */
    @RequiresGuest
    @PostMapping("registerByPhone")
    public Result registerByPhone(@RequestBody @Validated PhoneRegisterDto registerDto) {
        //验证 验证码是否正确
        if (kaptchaComponent.validate(registerDto.getVerifyCode()) &&
                smsComponent.validateSmsVerifyCode(registerDto.getUserPhone(), registerDto.getSmsVerifyCode())) {
            if (ObjectUtil.isEmpty(userService.getUserByPhone(registerDto.getUserPhone()))){
                if (userService.saveUserByPhone(registerDto.getUserPhone(), registerDto.getPassword())) {
                    //后续可直接登录
                    return Result.getSuccessRes(null);
                }
                return Result.getFailRes(FAIL_REGISTER_MESSAGE);
            }
            return Result.getFailRes(ALREADY_EXISTED_PHONE);
        }
        return Result.getFailRes(ERROR_VERIFY_CODE);
    }

    /**
     * 使用邮箱注册接口
     * @param registerDto 注册参数封装
     * @return 返回信息
     */
    @RequiresGuest
    @PostMapping("registerByEmail")
    public Result registerByEmail(@RequestBody @Validated EmailRegisterDto registerDto) {
        if (kaptchaComponent.validate(registerDto.getVerifyCode())) {
            if (ObjectUtil.isEmpty(userService.getUserByEmail(registerDto.getUserEmail()))) {
                if (userService.saveUserByEmail(registerDto.getUserEmail(), registerDto.getPassword())) {
                    return Result.getSuccessRes(true, SUCCESS_REGISTER_MESSAGE);
                }
                return Result.getFailRes(FAIL_REGISTER_MESSAGE);
            }
            return Result.getFailRes(ALREADY_EXISTED_EMAIL);
        }
        return Result.getFailRes(ERROR_VERIFY_CODE);
    }

    /**
     * 用户邮箱激活接口
     * @param md5Code 激活码
     * @return 返回结果
     */
    @RequiresGuest
    @GetMapping("activate/{md5Code}")
    public Result activateUser(@PathVariable String md5Code) {
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
            userService.updateUserStatusByUsername(username, EMAIL_ACTIVATE);
            return Result.getSuccessRes(username, "激活成功");
        }
        return Result.getFailRes("请点击正确的激活链接");
    }

    /**
     * 判断用户唯一信息是否存在
     * @param userInfo 用户信息
     * @return 返回结果
     */
    @RequiresGuest
    @GetMapping("isExistUser/{userInfo}")
    public Result isExistEmail(@PathVariable String userInfo) {
        User user = null;
        if (RegexUtils.phoneMatches(userInfo)) {
            user = userService.getUserByPhone(userInfo);
        }else if (RegexUtils.emailMatches(userInfo)) {
            user = userService.getUserByEmail(userInfo);
        }else if (RegexUtils.usernameMatches(userInfo)) {
            user = userService.getUserByUsername(userInfo);
        }
        if (ObjectUtil.isEmpty(user)) {
            return Result.getSuccessRes(true, "当前用户信息可注册");
        }
        return Result.getFailRes("该用户信息已被注册");
    }
}
