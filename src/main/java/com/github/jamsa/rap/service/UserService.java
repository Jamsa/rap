package com.github.jamsa.rap.service;

import com.github.jamsa.rap.core.mapper.BaseMapper;
import com.github.jamsa.rap.core.service.BaseService;
import com.github.jamsa.rap.mapper.UserMapper;
import com.github.jamsa.rap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Component;


/**
 * Created by zhujie on 16/7/5.
 */
@Component
public class UserService extends BaseService<User,Long>{
    @Autowired
    UserMapper userMapper;

    @Override
    public BaseMapper<User, Long> getMapper() {
        return this.userMapper;
    }

    @Override
    public int update(User record) {

         return super.update(record);
        //throw new DataAccessResourceFailureException("fdafd");

    }
}
