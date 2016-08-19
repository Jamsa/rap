package com.github.jamsa.rap.service;

import com.github.jamsa.rap.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * Created by zhujie on 16/8/16.
 */
public class UserServiceTest extends BaseTest {

    @Autowired
    UserService userService;

    @Test
    public void add() throws Exception {
        User u = new User();

        //userService.save(u);
    }

    @Test
    public void count(){
        Integer count = userService.queryForObject("select count(*) from User",new HashMap(),false);
        Assert.assertEquals(count,1);
    }
}
