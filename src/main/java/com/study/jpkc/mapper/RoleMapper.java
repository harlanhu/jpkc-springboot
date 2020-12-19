package com.study.jpkc.mapper;

import com.study.jpkc.entity.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 通过用户ID查询角色
     * @param userId 用户ID
     * @return 角色信息
     */
    @Select("select * from t_role where role_id in (select role_id from tm_user_role where user_id = #{userId})")
    List<Role> findRolesByUserId(String userId);
}
