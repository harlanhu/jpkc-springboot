package com.study.jpkc.mapper;

import com.study.jpkc.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/19 23:33
 * @desc
 */
@SpringBootTest
@Slf4j
public class RoleMapperTest {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    void findRoleByUserIdTest() {
        List<Role> roles = roleMapper.findRolesByUserId("0");
        log.info(String.valueOf(roles));
    }
}
