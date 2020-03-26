package com.macro.mall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macro.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：kzc
 **/
@Slf4j
public class AuthInterceptorHandler implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("进入前置拦截器");
        if(!ObjectUtils.isEmpty(request.getSession().getAttribute("member"))){
            return true;
        }
        print(response,"您没有权限访问！请先登录.");
        return false;
    }

    protected void print(HttpServletResponse response,String message) throws Exception{
        /**
         * 设置响应头
         */
        response.setHeader("Content-Type","application/json");
        response.setCharacterEncoding("UTF-8");
        String result = new ObjectMapper().writeValueAsString(CommonResult.forbidden(message));
        response.getWriter().print(result);

    }
}