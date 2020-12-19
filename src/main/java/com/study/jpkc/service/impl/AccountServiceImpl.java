package com.study.jpkc.service.impl;

import com.study.jpkc.service.IAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Harlan
 * @Date 2020/12/18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements IAccountService {
}
