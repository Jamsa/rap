package com.github.jamsa.rap.core.controller;

import com.github.jamsa.rap.core.model.BaseEntity;
import com.github.jamsa.rap.core.service.BaseEntityService;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by zhujie on 16/8/24.
 */
@RestController
public abstract class BaseEntityController<T extends BaseEntity<P>,P extends Serializable> extends BaseController {
    public abstract BaseEntityService<T,P> getService();

    @RequestMapping(value = "",method = {RequestMethod.GET})
    public PageInfo<T> findByPage(T condition, PageInfo<T> page){
        if(page.getPageSize()==0){
            page.setPageSize(10);
        }
        if(page.getPageNum()==0){
            page.setPageNum(1);
        }
        PageInfo<T> result = this.getService().findByPage(condition,page);
        return result;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public @ResponseBody T findByPrimaryKey(@PathVariable("id") P id){
        return this.getService().findByPrimaryKey(id);
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public @ResponseBody T save(@RequestBody T record){
        return this.getService().save(record);
    }

    @RequestMapping(value = "",method = RequestMethod.PUT)
    public @ResponseBody T update(@RequestBody T record){
        return this.getService().update(record);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public @ResponseBody int deleteByPrimaryKey(@PathVariable("id") P id){
        return this.getService().deleteByPrimaryKey(id);
    }


}
