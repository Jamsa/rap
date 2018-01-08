package com.github.jamsa.rap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.jamsa.rap.core.controller.BaseEntityController;
import com.github.jamsa.rap.core.service.BaseEntityService;
import com.github.jamsa.rap.model.User;
import com.github.jamsa.rap.service.UserService;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by zhujie on 16/7/5.
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseEntityController<User,Integer> {
    @Autowired
    UserService userService;

    @Override
    public BaseEntityService<User, Integer> getService() {
        return userService;
    }

}
