package com.study.jpkc.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.jpkc.entity.Permission;
import com.study.jpkc.entity.Role;
import com.study.jpkc.entity.User;
import com.study.jpkc.service.IPermissionService;
import com.study.jpkc.service.IRoleService;
import com.study.jpkc.service.IUserService;
import com.study.jpkc.utils.JwtUtils;
import com.study.jpkc.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/19 20:44
 * @desc 自定义realm
 */
@Component
@Slf4j
public class AccountRealm extends AuthorizingRealm {

    private final IUserService userService;

    private final IPermissionService permissionService;

    private final IRoleService roleService;

    private final RedisUtils redisUtils;

    private final JwtUtils jwtUtils;

    public AccountRealm(IUserService userService, RedisUtils redisUtils, JwtUtils jwtUtils, IPermissionService permissionService, IRoleService roleService) {
        this.userService = userService;
        this.redisUtils = redisUtils;
        this.jwtUtils = jwtUtils;
        this.permissionService = permissionService;
        this.roleService = roleService;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 认证登录
     * @param authenticationToken token
     * @return 登录
     * @throws AuthenticationException 异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        //解密获取username
        String username = jwtUtils.getClaimByToken((String) jwtToken.getCredentials()).getSubject();
        if (StringUtils.isBlank(username)) {
            throw new AuthenticationException("token无效");
        }
        //从redis中读取用户
        String token = (String) authenticationToken.getCredentials();
        AccountProfile accountProfile = (AccountProfile) redisUtils.get(token);
        if (accountProfile == null) {
            throw new AuthenticationException("token已过期");
        } else {
            redisUtils.expire(token, 60);
            return new SimpleAuthenticationInfo(accountProfile, jwtToken.getCredentials(), getName());
        }
    }


    /**
     * 授权
     * @param principalCollection 当前用户
     * @return 信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AccountProfile accountProfile = (AccountProfile) principalCollection.getPrimaryPrincipal();
        String username = accountProfile.getUsername();
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        List<String> permissionsName = new ArrayList<>();
        List<String> rolesName = new ArrayList<>();
        List<Role> roles = roleService.findRolesByUserId(user.getUserId());
        if (!roles.isEmpty()) {
            for (Role role : roles) {
                rolesName.add(role.getRoleName());
                List<Permission> permissions = permissionService.findPermissionsByRoleId(role.getRoleId());
                if (!permissions.isEmpty()) {
                    for (Permission permission : permissions) {
                        permissionsName.add(permission.getPermissionName());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //将角色放入shiro中
        info.addRoles(rolesName);
        //将权限放入shiro中
        info.addStringPermissions(permissionsName);
        return info;
    }
}
