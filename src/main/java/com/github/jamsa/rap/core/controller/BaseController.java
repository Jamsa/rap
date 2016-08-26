package com.github.jamsa.rap.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhujie on 16/8/24.
 */
public class BaseController {
    protected Logger logger  = LoggerFactory.getLogger(this.getClass());

    protected boolean isAjaxRequest(HttpServletRequest request){
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }
}
