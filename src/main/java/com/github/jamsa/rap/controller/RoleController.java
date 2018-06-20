package com.github.jamsa.rap.controller;

import javax.validation.Valid;

import com.github.jamsa.rap.model.Role;
import com.github.jamsa.rap.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.jamsa.rap.core.controller.BaseEntityController;
import com.github.jamsa.rap.core.service.BaseEntityService;


/**
 * Created by zhujie on 16/7/5.
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseEntityController<Role,Integer> {
    @Autowired
    RoleService roleService;

    @Override
    public BaseEntityService<Role, Integer> getService() {
        return roleService;
    }

}
