package com.macro.mall.service.impl;

import com.macro.excepiton.BusinessException;
import com.macro.mall.config.RedisConfiguration;
import com.macro.mall.config.properties.RedisKeyPrefixConfig;
import com.macro.mall.domain.Register;
import com.macro.mall.domain.UmsMember;
import com.macro.mall.domain.UmsMemberExample;
import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author kzc
 */

@Slf4j
@EnableConfigurationProperties(value = RedisKeyPrefixConfig.class)
@Transactional(rollbackFor = {Exception.class})
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private UmsMemberMapper memberMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisConfiguration redisConfiguration;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String getOtpCode(String phone) throws BusinessException {
        // 1.查询当前用户有没有注册
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<UmsMember> results = memberMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(results)){
            throw new BusinessException("用户已经注册！不能重复注册");
        }
        // 2.校验60s内有没有再次发送
        if(redisTemplate.hasKey(redisConfiguration.getPrefix().getOtpCode()+phone)){
            throw new BusinessException("60s后再试");
        }
        // 3.生成随机校验码
        Random random = new Random();
        String stb = random.nextInt(100000 + random.nextInt(99999))+"";
        log.info("otpCode{}",stb);
        redisTemplate.opsForValue().set(redisConfiguration.getPrefix().getOtpCode()+phone,
                stb,redisConfiguration.getExpire().getOtpCode(), TimeUnit.SECONDS);
        return stb;
    }

    @Override
    public int regite(Register register) throws BusinessException {

        String otpCode =(String) redisTemplate.opsForValue().get(redisConfiguration.getPrefix().getOtpCode()+register.getPhone());
        if(StringUtils.isEmpty(otpCode) || !otpCode.equals(register.getOtpCode())){
            throw new  BusinessException("校验码错误");
        }

        UmsMember member = new UmsMember();
        member.setStatus(1);
        member.setMemberLevelId(1L);
        BeanUtils.copyProperties(register,member);
        member.setPassword(passwordEncoder.encode(register.getPassword()));
        return memberMapper.insertSelective(member);
    }

    @Override
    public UmsMember login(String userName, String password) throws BusinessException {
        UmsMemberExample memberExample = new UmsMemberExample();
        memberExample.createCriteria().andUsernameEqualTo(userName).andStatusEqualTo(1);
        List<UmsMember> result = memberMapper.selectByExample(memberExample);
        if(CollectionUtils.isEmpty(result)){
            throw new BusinessException("用户名或密码不正确!");
        }
        if(result.size() > 1){
            throw new BusinessException("用户名被注册过多次,请联系客服!");
        }
        UmsMember member = result.get(0);
        if(!passwordEncoder.matches(password,member.getPassword())){
            throw new BusinessException("用户名或密码不正确!");
        }
        return member;

    }

   /* public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("admin"));
    }*/
}
