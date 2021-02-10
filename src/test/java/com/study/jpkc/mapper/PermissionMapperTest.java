package com.study.jpkc.mapper;

import com.study.jpkc.entity.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/19 23:42
 * @desc
 */
@SpringBootTest
class PermissionMapperTest {

    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    void findPermissionsByRoleIdTest() {
        List<Permission> permissions = permissionMapper.findPermissionsByRoleId("0");
        assertThat(permissions).isNotNull();
    }
}
