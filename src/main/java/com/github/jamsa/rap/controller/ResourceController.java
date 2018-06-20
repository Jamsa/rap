package com.github.jamsa.rap.controller;

import com.github.jamsa.rap.model.Resource;
import com.github.jamsa.rap.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jamsa.rap.core.controller.BaseEntityController;
import com.github.jamsa.rap.core.service.BaseEntityService;
import com.github.jamsa.rap.model.Role;
import com.github.jamsa.rap.service.RoleService;


/**
 * Created by zhujie on 16/7/5.
 */
@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseEntityController<Resource,Integer> {
    @Autowired
    ResourceService resourceService;

    @Override
    public BaseEntityService<Resource, Integer> getService() {
        return resourceService;
    }

}
