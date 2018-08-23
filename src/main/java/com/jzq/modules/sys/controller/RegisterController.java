package com.jzq.modules.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {
    private static Logger log = LoggerFactory.getLogger(RegisterController.class);

    @GetMapping("/register")
    public String register(){
        return "admin/register";
    }
}
