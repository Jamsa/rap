package com.github.jamsa.rap.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.jamsa.rap.model.User;
import com.github.pagehelper.PageInfo;

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
        u = new User();
        u.setUsername("xx");
        List<User> result = userService.findByCondition(u);
        for(User user :result){
            System.out.println(user.getUserId());
        }
        PageInfo<User> page = new PageInfo<User>();
        //page.setPageNum(1);
        //page.setPageSize(5);
        u.setUserId(3);
        page = userService.findByPage(u,page);
        System.out.println(page.getClass().getName());
        result = page.getList();
        for(User user : result){
            System.out.println(user.getUserId());
        }
    }

    @Test
    public void count(){

    }
}
