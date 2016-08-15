package com.github.jamsa.rap.controller;

import com.github.jamsa.rap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by zhujie on 16/7/5.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    @RequestMapping("/")
    private String index(Map<String,Object> model){
        return "user/list";
    }
}
