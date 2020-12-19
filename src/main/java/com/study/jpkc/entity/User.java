package com.study.jpkc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @TableId
    private String userId;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String userDesc;

    private Integer userSex;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate userBirthday;

    private String userPhone;

    @Email
    private String userEmail;

    private String userAvatar;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime userCreated;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime userLogin;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime userUpdate;


    public String getUserSex() {
        if (userSex == 0) {
            return "保密";
        }
        if (userSex == 1) {
            return "男";
        }
        if (userSex == 2) {
            return "女";
        }
        return null;
    }
}
