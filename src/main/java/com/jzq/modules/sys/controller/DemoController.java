package com.jzq.modules.sys.controller;

import com.jzq.modules.sys.entity.UserInfo;
import com.jzq.modules.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {
    private static Logger log = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value="/formDemo")
    public String formDemo(){
        return "admin/demo/formDemo";
    }

    @RequestMapping("/getUserList")
    public String getUserList(UserInfo userInfo, Model model){
        model.addAttribute("users",this.userService.getUserList(userInfo));
        return "admin/demo/listBak";
    }
    @RequestMapping("/errorDemo")
    public String errorDemo() throws  Exception{
        throw  new Exception();
    }
}
