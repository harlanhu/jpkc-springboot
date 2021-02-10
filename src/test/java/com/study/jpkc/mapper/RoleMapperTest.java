package com.study.jpkc.mapper;

import com.study.jpkc.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/19 23:33
 * @desc
 */
@SpringBootTest
class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    void findRoleByUserIdTest() {
        List<Role> roles = roleMapper.findRolesByUserId("0");
        assertThat(roles).isNotNull();
    }
}
