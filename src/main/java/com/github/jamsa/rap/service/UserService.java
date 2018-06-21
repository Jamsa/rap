package com.github.jamsa.rap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jamsa.rap.core.mapper.BaseMapper;
import com.github.jamsa.rap.core.service.BaseEntityService;
import com.github.jamsa.rap.mapper.UserMapper;
import com.github.jamsa.rap.model.User;


/**
 * Created by zhujie on 16/7/5.
 */
@Component
public class UserService extends BaseEntityService<User,Integer> {
    @Autowired
    UserMapper userMapper;

    @Override
    public BaseMapper<User, Integer> getMapper() {
        return this.userMapper;
    }

    public int deleteByPrimaryKey(Integer id){
        userMapper.deleteUserAllRoles(id);
        return this.getMapper().deleteByPrimaryKey(id);
    }

    public User save(User record){
        //保存主表
        this.getMapper().insert(record);
        if(!record.getRoles().isEmpty())
            userMapper.insertUserRoles(record.getUserId(),record.roles);
        return record;
    }

    public User update(User record){
        this.getMapper().updateByPrimaryKeySelective(record);
        userMapper.deleteUserAllRoles(record.getUserId());
        if(!record.getRoles().isEmpty())
            userMapper.insertUserRoles(record.getUserId(),record.getRoles());
        return record;
    }
}
