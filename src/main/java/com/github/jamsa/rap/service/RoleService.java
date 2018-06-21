package com.github.jamsa.rap.service;

import com.github.jamsa.rap.mapper.RoleMapper;
import com.github.jamsa.rap.model.Resource;
import com.github.jamsa.rap.model.Role;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jamsa.rap.core.mapper.BaseMapper;
import com.github.jamsa.rap.core.service.BaseEntityService;

import java.util.List;


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


    public int delete(Role record){
        roleMapper.deleteUserRoles(record.getRoleId());
        return this.getMapper().deleteByPrimaryKey(record.getPrimaryKey());
    }

    public Role save(Role record){
        this.getMapper().insert(record);
        if(!record.getAddResourceIds().isEmpty())
            roleMapper.insertRoleResources(record.getRoleId(),record.getAddResourceIds());
        return record;
    }

    public List<Resource> findRoleResources(Integer id){
        return roleMapper.selectRoleResources(id);
    }

    public Role update(Role record){
        this.getMapper().updateByPrimaryKeySelective(record);
        if(!record.getDelResourceIds().isEmpty())
            roleMapper.deleteRoleResources(record.getRoleId(),record.getDelResourceIds());
        if(!record.getAddResourceIds().isEmpty())
            roleMapper.insertRoleResources(record.getRoleId(),record.getAddResourceIds());
        return record;
    }

    public PageInfo<Resource> findRoleResources(Integer roleId, PageInfo<Resource> page){
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<Resource> result = roleMapper.selectRoleResources(roleId);
        PageInfo<Resource> pageInfo = new PageInfo<>(result);
        return pageInfo;
    }

}
