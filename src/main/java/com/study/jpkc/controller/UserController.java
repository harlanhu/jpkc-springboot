package com.study.jpkc.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.jpkc.common.lang.Result;
import com.study.jpkc.entity.User;
import com.study.jpkc.service.IUserService;
import com.study.jpkc.shiro.AccountProfile;
import com.study.jpkc.utils.Base64Utils;
import com.study.jpkc.utils.RedisUtils;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtils redisUtils;

    private static final String FAIL_REGISTER_MESSAGE = "注册失败，请稍后再试";
    private static final String SUCCESS_REGISTER_MESSAGE = "注册成功";

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

    @RequiresGuest
    @GetMapping("activate/{baseCode}")
    public Result activate(@PathVariable String baseCode) {
        String[] codes = Base64Utils.decode(baseCode).split("/");
        String key = codes[0] + codes[2];
        if (codes[1].equals(redisUtils.get(key))) {
            return Result.getSuccessRes(codes[0], codes[1]);
        }
        return Result.getFailRes();
    }

}
