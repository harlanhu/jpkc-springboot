package com.study.jpkc.mapper;

import com.study.jpkc.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
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
    @Select("select * from t_permission where permission_id in (select permission_id from tm_role_permission where role_id = #{roleId})")
    List<Permission> findPermissionsByRoleId(String roleId);
}
