package com.study.jpkc.service;

import com.study.jpkc.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-19
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 通过角色ID查询权限信息
     * @param roleId 角色ID
     * @return 权限信息
     */
    List<Permission> findPermissionsByRoleId(String roleId);
}
