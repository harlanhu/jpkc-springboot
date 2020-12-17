package com.study.jpkc.service.impl;

import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Harlan
 * @Date 2020/12/17
 */
@Transactional(rollbackFor = Exception.class)
public class HelloServiceImpl {
}
