package com.study.jpkc.mapper;

import com.study.jpkc.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Harlan
 * @Date 2021/1/4
 */
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void updateUserStatusByUsername() {
        int res = userMapper.updateUserStatusByUsername("test", 0);
        assert res == 1;
    }

    @Test
    void insertUserAndRoleTest() {
        User u = new User();
        u.setUserId("983e9121bb1d43bc8d243014240c0894");
        int res = userMapper.insertUserAndRole(u, "USER");
        assert res == 1;
    }
}
