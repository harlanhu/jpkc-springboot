package com.study.jpkc.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.jpkc.common.component.KaptchaComponent;
import com.study.jpkc.common.component.SmsComponent;
import com.study.jpkc.common.dto.DefaultRegisterDto;
import com.study.jpkc.common.dto.EmailRegisterDto;
import com.study.jpkc.common.dto.PhoneRegisterDto;
import com.study.jpkc.common.lang.PageVo;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.User;
import com.study.jpkc.service.IUserService;
import com.study.jpkc.shiro.AccountProfile;
import com.study.jpkc.utils.RedisUtils;
import com.study.jpkc.utils.RegexUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    private final IUserService userService;

    private final RedisUtils redisUtils;

    private final KaptchaComponent kaptchaComponent;

    private final SmsComponent smsComponent;

    private static final Integer EMAIL_ACTIVATE = 0;
    private static final String FAIL_REGISTER_MESSAGE = "注册失败，请稍后再试";
    private static final String SUCCESS_REGISTER_MESSAGE = "注册成功";
    private static final String ALREADY_EXISTED_USERNAME = "该用户名已被注册";
    private static final String ALREADY_EXISTED_EMAIL = "该邮箱已被注册";
    private static final String ALREADY_EXISTED_PHONE = "该手机已被注册";
    private static final String ERROR_VERIFY_CODE = "验证码错误";

    public UserController(IUserService userService, RedisUtils redisUtils, KaptchaComponent kaptchaComponent, SmsComponent smsComponent) {
        this.userService = userService;
        this.redisUtils = redisUtils;
        this.kaptchaComponent = kaptchaComponent;
        this.smsComponent = smsComponent;
    }


    /**
     * 用户注册接口
     * @param user 用户信息
     * @return 返回结果
     */
    @RequiresGuest
    @PostMapping("register")
    public Result register(@RequestBody @Validated User user) {
        User userOfName = userService.getByUsername(user.getUsername());
        if (ObjectUtil.isNotEmpty(userOfName)) {
            return Result.getFailRes(ALREADY_EXISTED_USERNAME);
        }
        User userOfEmail = userService.getByEmail(user.getUserEmail());
        if (ObjectUtil.isNotEmpty(userOfEmail)) {
            return Result.getFailRes(ALREADY_EXISTED_EMAIL);
        }
        User userOfPhone = userService.getByPhone(user.getUserPhone());
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
            if (ObjectUtil.isEmpty(userService.getByPhone(registerDto.getUserPhone()))){
                if (userService.saveByPhone(registerDto.getUserPhone(), registerDto.getPassword())) {
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
            if (ObjectUtil.isEmpty(userService.getByEmail(registerDto.getUserEmail()))) {
                if (userService.saveByEmail(registerDto.getUserEmail(), registerDto.getPassword())) {
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
            userService.updateStatusByUsername(username, EMAIL_ACTIVATE);
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
    public Result isExistUser(@PathVariable String userInfo) {
        if (isExist(userInfo)) {
            return Result.getSuccessRes(true, "当前用户信息可注册");
        }
        return Result.getFailRes("该用户信息已被注册");
    }

    /**
     * 默认用户注册接口
     * @param registerDto dto
     * @return 返回结果
     */
    @RequiresGuest
    @PostMapping("/userRegister")
    public Result userRegister(@RequestBody DefaultRegisterDto registerDto) {
        if (smsComponent.validateSmsVerifyCode(registerDto.getUserPhone(), registerDto.getSmsVerifyCode()) &&
                isExist(registerDto.getUserEmail()) &&
                isExist(registerDto.getUserPhone())) {
            if (userService.registerDefaultUser(registerDto.getUserPhone(), registerDto.getUserEmail(), registerDto.getPassword())) {
                return Result.getSuccessRes(SUCCESS_REGISTER_MESSAGE);
            }
            return Result.getFailRes("服务器正忙，请稍后再试");
        }
        return Result.getFailRes(FAIL_REGISTER_MESSAGE);
    }

    /**
     * 判断用户信息是否被注册
     * @param userInfo 用户信息
     * @return 是否注册
     */
    public boolean isExist(String userInfo) {
        User user = null;
        if (RegexUtils.phoneMatches(userInfo)) {
            user = userService.getByPhone(userInfo);
        }else if (RegexUtils.emailMatches(userInfo)) {
            user = userService.getByEmail(userInfo);
        }else if (RegexUtils.usernameMatches(userInfo)) {
            user = userService.getByUsername(userInfo);
        }
        return user == null;
    }

    @GetMapping("getAll/{current}/{size}")
    public Result getAll(@PathVariable int current, @PathVariable int size) {
        return Result.getSuccessRes(PageVo.getPageVo(userService.page(new Page<>(current, size))));
    }

    @RequiresUser
    @GetMapping("/getByUser")
    public Result getByUser() {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        User user = userService.getById(account.getUserId());
        user.setPassword("********");
        user.setUserPhone(user.getUserPhone().substring(0,3) + "****" + user.getUserPhone().substring(7,10));
        user.setUserEmail(user.getUserEmail().substring(0,3) + "******" + user.getUserEmail().substring(5));
        return Result.getSuccessRes(user);
    }

    @RequiresUser
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        System.out.println(user);
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        user.setUserId(account.getUserId());
        boolean isSuccess = userService.updateById(user);
        if (isSuccess) {
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes();
        }
    }

    @RequiresUser
    @GetMapping("/sendPhoneCodeByUser")
    public Result sendPhoneCodeByUser() {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        String message = smsComponent.sendSmsVerifyCode(account.getUserPhone());
        return Result.getSuccessRes(message);
    }

    @RequiresUser
    @GetMapping("/verifyCode/{code}")
    public Result verifyCode(@PathVariable String code) {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        boolean isSuccess = smsComponent.validateSmsVerifyCode(account.getUserPhone(), code);
        if (isSuccess) {
            return Result.getSuccessRes("验证码正确");
        }
        return Result.getFailRes("验证码错误");
    }

    @RequiresUser
    @GetMapping("/updatePhone/{phone}/{code}")
    public Result updatePhone(@PathVariable String phone, @PathVariable String code) {
        smsComponent.validateSmsVerifyCode(phone, code);
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        boolean isSuccess = userService.updatePhone(account.getUserId(), phone);
        if (isSuccess) {
            return Result.getSuccessRes(null);
        }
        return Result.getFailRes();
    }

    @SneakyThrows
    @RequiresUser
    @PostMapping("/uploadAvatar")
    public Result uploadUserAvatar(@RequestParam MultipartFile file) {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        boolean isSuccess = userService.uploadAvatar(account.getUserId(), file);
        if (isSuccess) {
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes();
        }
    }

    @RequiresUser
    @GetMapping("/sendMailVerifyCode")
    public Result updateEmail() {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        userService.sendMailVerify(account.getUserId());
        return Result.getSuccessRes(null);
    }

    @RequiresUser
    @GetMapping("/updateEmail/{email}/{code}")
    public Result updateEmail(@PathVariable String email, @PathVariable String code) {
        return null;
    }

    @RequiresUser
    @GetMapping("/verifyMailCode/{code}")
    public Result verifyMailCode(@PathVariable String code) {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        String realCode = (String) redisUtils.get("verify-" + account.getUserEmail());
        if (StringUtils.isEmpty(realCode)) {
            return Result.getFailRes("验证码失效，请重新获取");
        } else if (realCode.equals(code)) {
            redisUtils.del("verify-" + account.getUserEmail());
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes("验证码错误，请重新输入");
        }
    }

    @RequiresUser
    @GetMapping("/getMailVerify/{mail}")
    public Result getMailVerify(@PathVariable String mail) {
        userService.getMailVerify(mail);
        return Result.getSuccessRes(null, "发送成功");
    }

    @RequiresUser
    @GetMapping("/updateMail/{mail}/{code}")
    public Result updateMail(@PathVariable String mail, @PathVariable String code) {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        String realCode = (String) redisUtils.get("verify-" + mail);
        if (StringUtils.isEmpty(realCode)) {
            return Result.getFailRes("验证码已过期，请重新获取");
        } else if (!realCode.equals(code)) {
            return Result.getFailRes("验证码错误，请重新输入");
        }
        redisUtils.del("verify-" + mail);
        boolean isSuccess = userService.updateEmail(account.getUserId(), mail);
        if (isSuccess) {
            return Result.getSuccessRes(null, "修改成功");
        }
        return Result.getFailRes("修改失败，请稍后再试");
    }

    @GetMapping("/verifyPassword/{password}")
    public Result verifyPassword(@PathVariable("password") String password) {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        User user = userService.getById(account.getUserId());
        if (user.getPassword().equals(new SimpleHash("MD5", password).toHex())) {
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes();
        }
    }

    @GetMapping("/updatePassword/{newPassword}")
    public Result updatePassword(@PathVariable("newPassword") String newPassword) {
        AccountProfile account = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
        User user = userService.getById(account.getUserId());
        user.setPassword(new SimpleHash("MD5", newPassword).toHex());
        smsComponent.sendInfoEditedMessage(user);
        if (userService.updateById(user)) {
            return Result.getSuccessRes(null);
        } else {
            return Result.getFailRes();
        }
    }
}
