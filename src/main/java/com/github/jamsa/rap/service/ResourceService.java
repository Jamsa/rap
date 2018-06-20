package com.github.jamsa.rap.service;

import com.github.jamsa.rap.mapper.ResourceMapper;
import com.github.jamsa.rap.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jamsa.rap.core.mapper.BaseMapper;
import com.github.jamsa.rap.core.service.BaseEntityService;
import com.github.jamsa.rap.mapper.RoleMapper;
import com.github.jamsa.rap.model.Role;


/**
 * Created by zhujie on 16/7/5.
 */
@Component
public class ResourceService extends BaseEntityService<Resource,Integer> {
    @Autowired
    ResourceMapper resourceMapper;

    @Override
    public BaseMapper<Resource, Integer> getMapper() {
        return this.resourceMapper;
    }

}
