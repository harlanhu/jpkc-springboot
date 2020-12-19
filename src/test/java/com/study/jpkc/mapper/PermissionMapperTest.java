package com.study.jpkc.mapper;

import com.study.jpkc.entity.Permission;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/19 23:42
 * @desc
 */
@SpringBootTest
@Slf4j
public class PermissionMapperTest {

    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    void findPermissionsByRoleIdTest() {
        List<Permission> permissions = permissionMapper.findPermissionsByRoleId("0");
        log.info(String.valueOf(permissions));
    }
}
