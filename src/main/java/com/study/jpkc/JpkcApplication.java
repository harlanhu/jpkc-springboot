package com.study.jpkc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Harlan
 */
@SpringBootApplication
@MapperScan("com.study.jpkc.mapper")
public class JpkcApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpkcApplication.class, args);
    }

}
