package com.study.jpkc.common.dto;

import com.study.jpkc.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author isharlan.hu@gmail.com
 * @date 2020/12/21 22:11
 * @desc 邮件注册dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMailDto implements Serializable {
    private String activateUrl;
    private User user;
}
