package com.github.jamsa.rap.controller;

import javax.validation.Valid;

import com.github.jamsa.rap.model.Resource;
import com.github.jamsa.rap.model.Role;
import com.github.jamsa.rap.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(value = "{id}/resources",method = {RequestMethod.GET})
    public ResponseEntity findRoleResourcesByPage(@PathVariable("id") Integer id, PageInfo<Resource> page){

        if(page.getPageSize()==0){
            page.setPageSize(10);
        }
        if(page.getPageNum()==0){
            page.setPageNum(1);
        }
        PageInfo<Resource> result = roleService.findRoleResources(id,page);
        return ResponseEntity.ok(result);
    }

}
