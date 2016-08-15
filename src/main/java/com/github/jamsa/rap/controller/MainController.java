package com.github.jamsa.rap.controller;

import com.github.jamsa.rap.model.User;
import com.github.jamsa.rap.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhujie on 16/7/4.
 */
@Controller()
public class MainController {
    public static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value="/",method = RequestMethod.GET)
    public String index(Map<String,Object> model){
        model.put("title","标题");
        model.put("msg","内容");
        /*
        List<User> users = userService.findById();
        for(User user:users){
            logger.info(user.getWorkTicketId()+"");
        }*/
        return "index";
    }
}
