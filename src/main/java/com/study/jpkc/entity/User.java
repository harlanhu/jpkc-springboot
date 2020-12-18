package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author isharlan.hu@gmail.com
 * @since 2020-12-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String username;

    private String password;

    private String userDesc;

    private Integer userSex;

    private LocalDate userBirthday;

    private String userPhone;

    private String userEmail;

    private String userAvatar;

    private LocalDateTime userCreated;

    private LocalDateTime userLogin;

    private LocalDateTime userUpdate;


}
