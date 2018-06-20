package com.github.jamsa.rap.service;

import com.github.jamsa.rap.mapper.RoleMapper;
import com.github.jamsa.rap.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jamsa.rap.core.mapper.BaseMapper;
import com.github.jamsa.rap.core.service.BaseEntityService;


/**
 * Created by zhujie on 16/7/5.
 */
@Component
public class RoleService extends BaseEntityService<Role,Integer> {
    @Autowired
    RoleMapper roleMapper;

    @Override
    public BaseMapper<Role, Integer> getMapper() {
        return this.roleMapper;
    }

}
