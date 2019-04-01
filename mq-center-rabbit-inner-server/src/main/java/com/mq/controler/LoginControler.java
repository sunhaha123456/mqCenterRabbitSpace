package com.mq.controler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 功能：登录 controller
 * @author sunpeng
 * @date 2018
 */
@Slf4j
@Controller
@RequestMapping("/login")
public class LoginControler {

    @GetMapping(value = "/toLogin")
    public String toLogin() {
        return "login";
    }
}