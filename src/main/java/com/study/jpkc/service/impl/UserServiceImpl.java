package com.study.jpkc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.jpkc.common.dto.RegisterMailDto;
import com.study.jpkc.entity.User;
import com.study.jpkc.mapper.UserMapper;
import com.study.jpkc.service.IUserService;
import com.study.jpkc.utils.Base64Utils;
import com.study.jpkc.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
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

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RabbitMessagingTemplate messagingTemplate;

    private final int ACTIVATE_KEY_SAVE_TIME = 60 * 60 * 24 * 3;

    @Override
    public boolean save(User user) {
        if (StringUtils.isBlank(user.getUserAvatar())) {
            user.setUserAvatar("https://study-1259382847.cos.ap-chongqing.myqcloud.com/jpck/user/avatar/default/default_avatar.jpg");
        }
        String password = user.getPassword();
        user.setPassword(new SimpleHash("MD5", password).toHex());
        user.setUserId(UUID.randomUUID().toString().replace("-", ""));
        if (userMapper.insert(user) == 0) {
            return false;
        }
        //加密邮件激活密钥地址 Base64 密钥=当前时间+/+userId+/+随机4位数字
        String time = LocalDateTime.now().toString();
        String randomNum = String.valueOf(new Random().nextInt(9999));
        String activateKey = time + "/" + user.getUserId() + "/" + randomNum;
        //信息存入redis key=时间+随机数:value=userId
        redisUtils.set(time+randomNum, user.getUserId(), ACTIVATE_KEY_SAVE_TIME);
        String activateUrl = Base64Utils.encode(activateKey);
        messagingTemplate.convertAndSend("amq.direct", "user.register.mail", new RegisterMailDto(activateUrl, user));
        return true;
    }
}
