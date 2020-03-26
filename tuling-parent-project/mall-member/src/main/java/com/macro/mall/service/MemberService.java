package com.macro.mall.service;

import com.macro.excepiton.BusinessException;
import com.macro.mall.domain.Register;
import com.macro.mall.domain.UmsMember;

/**
 * @author kzc
 */
public interface MemberService {

    String getOtpCode(String phone) throws BusinessException;

    /**
     * 用户简单信息注册
     * @param register
     * @return
     */
    int regite(Register register) throws BusinessException;

    UmsMember login(String userName, String password) throws BusinessException;
}
