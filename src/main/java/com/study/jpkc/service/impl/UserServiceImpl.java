package com.study.jpkc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.entity.User;
import com.study.jpkc.mapper.UserMapper;
import com.study.jpkc.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean save(User entity) {
        if (StringUtils.isBlank(entity.getUserAvatar())) {
            entity.setUserAvatar("https://study-1259382847.cos.ap-chongqing.myqcloud.com/jpck/user/avatar/default/default_avatar.jpg");
        }
        String password = entity.getPassword();
        entity.setPassword(new SimpleHash("MD5", password).toHex());
        entity.setUserId(UUID.randomUUID().toString().replace("-", ""));
        return userMapper.insert(entity) == 0;
    }
}
