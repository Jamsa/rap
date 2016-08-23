package com.github.jamsa.rap.service;

import com.github.jamsa.rap.mapper.UserMapper;
import com.github.jamsa.rap.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.dao.DataAccessResourceFailureException;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * Created by zhujie on 16/8/16.
 */
public class UserServiceTest extends BaseTest {

    @Autowired
    UserService userService;


    @Test
    public void crud() throws Exception {
        User u = new User();
        u.setUsername("xxxxx");
        userService.save(u);
        //u.setUsername("xxxxx1");
        u.setFullname("bbb");
        userService.update(u);
        userService.delete(u);
    }

    @Test
    public void count(){

    }
}
