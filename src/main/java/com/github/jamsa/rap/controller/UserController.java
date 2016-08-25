package com.github.jamsa.rap.controller;

import com.github.jamsa.rap.core.controller.BaseEntityController;
import com.github.jamsa.rap.core.service.BaseEntityService;
import com.github.jamsa.rap.model.User;
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
public class UserController extends BaseEntityController<User,Long> {
    @Autowired
    UserService userService;

    @Override
    public BaseEntityService<User, Long> getService() {
        return userService;
    }
}
