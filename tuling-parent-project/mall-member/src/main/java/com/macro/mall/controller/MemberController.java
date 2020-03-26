package com.macro.mall.controller;

import com.macro.api.CommonResult;
import com.macro.excepiton.BusinessException;
import com.macro.mall.domain.Register;
import com.macro.mall.domain.UmsMember;
import com.macro.mall.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author kzc
 */

@RestController
@RequestMapping("/member")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/getOtpCode")
    public CommonResult getOtpCode(@RequestParam String phone) throws BusinessException {
        String otpCode = memberService.getOtpCode(phone);
        return CommonResult.success(otpCode);
    }

    @PostMapping("/regite")
    public CommonResult regite(@RequestBody @Valid Register register) throws BusinessException {
        int result = memberService.regite(register);
        if(result > 0){
            CommonResult.success(result);
        }
        return CommonResult.failed();
    }

    @PostMapping("login")
    public CommonResult login(@RequestParam String userName, @RequestParam String password) throws BusinessException {
        UmsMember member = memberService.login(userName, password);
        if(member != null){
            return CommonResult.success(member.getUsername() + "登陆成功");
        }
        return null;
    }
}
