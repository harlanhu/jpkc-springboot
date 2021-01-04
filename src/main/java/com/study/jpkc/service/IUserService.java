package com.study.jpkc.service;

import com.study.jpkc.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
public interface IUserService extends IService<User> {

    /**
     * 通过邮箱查询用户
     * @param email 邮箱
     * @return 用户信息
     */
    User getUserByEmail(String email);

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    User getUserByUsername(String username);

    /**
     * 通过用户手机查询用户
     * @param phone 用户手机
     * @return 用户信息
     */
    User getUserByPhone(String phone);

    /**
     * 通过邮箱保存用户
     * @param email 邮箱
     * @param password 用户密码
     * @return 是否成功
     */
    boolean saveUserByEmail(String email, String password);

    /**
     * 通过手机号码保存用户
     * @param phone 用户手机
     * @param password 用户密码
     * @return 是否成功
     */
    boolean saveUserByPhone(String phone, String password);

    /**
     * 通过用户名更改用户信息
     * @param username 用户名
     * @param status 用户状态
     * @return 是否成功
     */
    boolean updateUserStatusByUsername(String username, Integer status);
}
