package com.github.jamsa.rap.core.controller;

import com.github.jamsa.rap.core.model.BaseEntity;
import com.github.jamsa.rap.core.service.BaseEntityService;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by zhujie on 16/8/24.
 */
public abstract class BaseEntityController<T extends BaseEntity<P>,P extends Serializable> extends BaseController {
    public abstract BaseEntityService<T,P> getService();
    public abstract String getViewDir();

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(ModelMap map, HttpServletRequest request){
        map.put("isAjaxRequest",isAjaxRequest(request));
        return this.getViewDir()+"/list";
    }

    @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
    public String edit(ModelMap map, HttpServletRequest request, @PathVariable("id") P id){
        map.put("isAjaxRequest",isAjaxRequest(request));
        map.put("record",this.getService().findByPrimaryKey(id));
        map.put("isAdd",Boolean.FALSE);
        return this.getViewDir()+"/edit";
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(ModelMap map, HttpServletRequest request){
        map.put("isAjaxRequest",isAjaxRequest(request));
        map.put("isAdd",Boolean.TRUE);
        return this.getViewDir()+"/edit";
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public @ResponseBody
    PageInfo<T> list(T condition, PageInfo<T> page){
        if(page.getPageSize()==0){
            page.setPageSize(10);
        }
        if(page.getPageNum()==0){
            page.setPageNum(1);
        }
        PageInfo<T> result = this.getService().findByPage(condition,page);
        System.out.println(result);
        return result;
    }


    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    public @ResponseBody T getByPrimaryKey(@PathVariable("id") P id){
        return this.getService().findByPrimaryKey(id);
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public @ResponseBody T save(T record){
        return this.getService().save(record);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public @ResponseBody T update(T record){
        return this.getService().update(record);
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    public @ResponseBody int deleteByPrimaryKey(@PathVariable("id") P id){
        return this.getService().deleteByPrimaryKey(id);
    }


}
