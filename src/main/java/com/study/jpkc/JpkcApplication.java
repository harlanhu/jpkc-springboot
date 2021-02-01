package com.study.jpkc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Harlan
 */
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties
public class JpkcApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpkcApplication.class, args);
    }

}
