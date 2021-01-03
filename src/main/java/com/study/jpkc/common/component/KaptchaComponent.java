package com.study.jpkc.common.component;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.kaptcha.Kaptcha;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaRenderException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.study.jpkc.utils.RedisUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author Harlan
 * @Date 2020/12/23
 * @desc 验证码图片组件
 */
@Component
@Slf4j
public class KaptchaComponent implements Kaptcha {

    private DefaultKaptcha kaptcha;

    private RedisUtils redisUtils;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    public KaptchaComponent(DefaultKaptcha kaptcha, RedisUtils redisUtils) {
        this.kaptcha = kaptcha;
        this.redisUtils = redisUtils;
    }

    @Override
    public String render() {
        //判断当前是否已经获取过验证码
        String usersKey = request.getHeader("KaptchaCode");
        String key;
        if (StringUtils.isBlank(usersKey)) {
            key = UUID.randomUUID().toString().replace("-", "");
        }else {
            key = usersKey;
        }
        this.response.setDateHeader("Expires", 0L);
        this.response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        this.response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        this.response.setHeader("Pragma", "no-cache");
        this.response.setContentType("image/jpeg");
        String sessionCode = this.kaptcha.createText();
        try {
            ServletOutputStream out = this.response.getOutputStream();
            Throwable var3 = null;
            String var4;
            try {
                this.response.setHeader("KaptchaCode", key);
                redisUtils.set(key, sessionCode, 60L * 10L);
                ImageIO.write(this.kaptcha.createImage(sessionCode), "jpg", out);
                var4 = sessionCode;
            } catch (Throwable var14) {
                var3 = var14;
                throw var14;
            } finally {
                if (out != null) {
                    if (var3 != null) {
                        try {
                            out.close();
                        } catch (Throwable var13) {
                            var3.addSuppressed(var13);
                        }
                    } else {
                        out.close();
                    }
                }

            }

            return var4;
        } catch (IOException var16) {
            throw new KaptchaRenderException(var16);
        }
    }

    @Override
    public boolean validate(@NonNull String code) {
        if (ObjectUtil.isEmpty(code)) {
            throw new NullPointerException("code");
        } else {
            String key;
            if ((key = request.getHeader("KaptchaCode")) != null) {
                if (redisUtils.hasKey(key)) {
                    String vCode = (String) redisUtils.get(key);
                    if (code.equals(vCode)) {
                        redisUtils.del(key);
                        return true;
                    }else {
                        throw new KaptchaIncorrectException();
                    }
                }else {
                    throw new KaptchaTimeoutException();
                }
            } else {
                throw new KaptchaNotFoundException();
            }
        }
    }

    /**
     * 判断验证码是否正确
     * @param code 验证码
     * @return 是否正确
     */
    public boolean isValidate(@NonNull String code) {
        if (ObjectUtil.isEmpty(code)) {
            throw new NullPointerException("code");
        } else {
            String key;
            if ((key = request.getHeader("KaptchaCode")) != null) {
                if (redisUtils.hasKey(key)) {
                    String vCode = (String) redisUtils.get(key);
                    if (code.equals(vCode)) {
                        return true;
                    }else {
                        throw new KaptchaIncorrectException();
                    }
                }else {
                    throw new KaptchaTimeoutException();
                }
            } else {
                throw new KaptchaNotFoundException();
            }
        }
    }

    @Override
    public boolean validate(String s, long l) {
        return false;
    }
}
