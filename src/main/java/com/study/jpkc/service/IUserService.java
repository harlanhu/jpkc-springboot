package com.study.jpkc.service;

import com.study.jpkc.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    User getByEmail(String email);

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);

    /**
     * 通过用户手机查询用户
     * @param phone 用户手机
     * @return 用户信息
     */
    User getByPhone(String phone);

    /**
     * 通过邮箱保存用户
     * @param email 邮箱
     * @param password 用户密码
     * @return 是否成功
     */
    boolean saveByEmail(String email, String password);

    /**
     * 通过手机号码保存用户
     * @param phone 用户手机
     * @param password 用户密码
     * @return 是否成功
     */
    boolean saveByPhone(String phone, String password);

    /**
     * 通过用户名更改用户信息
     * @param username 用户名
     * @param status 用户状态
     * @return 是否成功
     */
    boolean updateStatusByUsername(String username, Integer status);

    /**
     * 通过手机号和邮箱及密码保存用户
     * @param userPhone 手机号
     * @param userEmail 邮箱
     * @param userPassword 密码
     * @return 是否成功
     */
    boolean registerDefaultUser(String userPhone, String userEmail, String userPassword);

    /**
     * 通过课程id查询用户
     * @param courseId 课程id
     * @return 用户
     */
    User getByCourseId(String courseId);

    /**
     * 上传头像
     * @param userId 用户id
     * @param file 头像
     * @throws IOException 异常
     * @return 是否成功
     */
    boolean uploadAvatar(String userId, MultipartFile file) throws IOException;


    boolean updatePhone(String userId, String phone);
}
