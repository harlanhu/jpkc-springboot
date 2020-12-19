package com.study.jpkc.entity;

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
public class Account implements Serializable {

    private String username;

    private String phone;

    private String email;

    private String password;
}
