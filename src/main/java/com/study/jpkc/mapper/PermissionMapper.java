package com.study.jpkc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.jpkc.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-19
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 通过角色ID查询权限信息
     * @param roleId 角色ID
     * @return 权限信息
     */
    List<Permission> findPermissionsByRoleId(@Param("roleId") String roleId);
}
