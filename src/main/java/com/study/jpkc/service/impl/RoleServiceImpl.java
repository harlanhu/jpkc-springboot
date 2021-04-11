package com.study.jpkc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.entity.Role;
import com.study.jpkc.mapper.RoleMapper;
import com.study.jpkc.service.IRoleService;
import com.study.jpkc.utils.GenerateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public List<Role> findRolesByUserId(String userId) {
        return roleMapper.findRolesByUserId(userId);
    }

    @Override
    public boolean bindTeacher(String userId) {
        return roleMapper.bindRole(GenerateUtils.getUUID(), userId, "bfe1c27eaf485230a78627a9e31d0fsa") == 1;
    }
}
