package com.github.jamsa.rap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.jamsa.rap.core.controller.BaseEntityController;
import com.github.jamsa.rap.core.service.BaseEntityService;
import com.github.jamsa.rap.model.User;
import com.github.jamsa.rap.service.UserService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


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

    //@RequiresPermissions("UserController:add")
    @Override
    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody @Valid User record, Errors errors){
        record.setPassword("password");
        return super.save(record,errors);
    }

}
