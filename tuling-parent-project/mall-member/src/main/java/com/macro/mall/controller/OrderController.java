package com.macro.mall.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kzc
 */
@Slf4j
@RestController
public class OrderController {

    @GetMapping("/order")
    public String order(){
        log.info("下订单->jvm-8081");
        return "下订单->jvm-8081";
        /*log.info("下订单->jvm-8080");
        return "下订单->jvm-8080";*/
    }

}
