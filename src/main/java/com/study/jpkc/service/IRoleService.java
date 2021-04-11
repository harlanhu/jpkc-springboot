package com.study.jpkc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.jpkc.entity.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-19
 */
public interface IRoleService extends IService<Role> {

    /**
     * 通过用户ID查询角色信息
     * @param userId 用户ID
     * @return 角色信息
     */
    List<Role> findRolesByUserId(String userId);

    /**
     * 绑定教师角色
     * @param userId 用户id
     * @return 是否成功
     */
    boolean bindTeacher(String userId);
}
