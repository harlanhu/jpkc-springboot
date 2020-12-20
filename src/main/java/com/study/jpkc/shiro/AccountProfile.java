package com.study.jpkc.shiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 账户实体
 * @Author Harlan
 * @Date 2020/12/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountProfile implements Serializable {

    private String userId;

    private String username;

    private String userPhone;

    private String userEmail;

    private String userAvatar;

}
