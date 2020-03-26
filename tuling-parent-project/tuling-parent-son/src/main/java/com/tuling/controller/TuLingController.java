package com.tuling.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kzc
 */

@RestController
public class TuLingController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
