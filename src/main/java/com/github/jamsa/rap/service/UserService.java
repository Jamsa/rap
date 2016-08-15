package com.github.jamsa.rap.service;

import com.github.jamsa.rap.mapper.UserMapper;
import com.github.jamsa.rap.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zhujie on 16/7/5.
 */
@Component
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<User> findById(){
        return userMapper.selectForPage();
    }
}
