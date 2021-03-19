package com.study.jpkc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.jpkc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过用户名更新用户状态
     * @param username 用户名
     * @param status 用户状态
     * @return 影响行数
     */
    int updateUserStatusByUsername(@Param("username") String username, @Param("status") Integer status);

    /**
     * 绑定用户与角色
     * @param user 用户
     * @param roleName 角色名称
     * @return 影响行数
     */
    int insertUserAndRole(@Param("user") User user,@Param("roleName") String roleName);

    /**
     * 通过课程id查询用户
     * @param courseId 课程id
     * @return 用户信息
     */
    User selectByCourseId(String courseId);
}
