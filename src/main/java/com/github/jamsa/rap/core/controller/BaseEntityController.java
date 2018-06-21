package com.github.jamsa.rap.core.controller;

import com.github.jamsa.rap.core.model.BaseEntity;
import com.github.jamsa.rap.core.service.BaseEntityService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by zhujie on 16/8/24.
 */
@RestController
public abstract class BaseEntityController<T extends BaseEntity<P>,P extends Serializable> extends BaseController {
    public abstract BaseEntityService<T,P> getService();


    protected void checkPermissions(String permissions){
        //TODO
        //if(!SecurityUtils.getSubject().isPermitted(this.getClass().getSimpleName()+":"+permissions)) throw new AuthorizationException("aaa");
    }

    @RequestMapping(value = "",method = {RequestMethod.GET})
    public ResponseEntity findByPage(T condition, PageInfo<T> page){
        checkPermissions("view");

        if(page.getPageSize()==0){
            page.setPageSize(10);
        }
        if(page.getPageNum()==0){
            page.setPageNum(1);
        }
        PageInfo<T> result = this.getService().findByPage(condition,page);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity findByPrimaryKey(@PathVariable("id") P id){
        checkPermissions("view");
        T result = this.getService().findByPrimaryKey(id);
        if(result!=null){
            return ResponseEntity.ok(result);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    protected String getValidateErrors(Errors errors){
        if(errors.hasErrors()) {
            StringBuffer buffer = new StringBuffer();
            for(Iterator<ObjectError> it = errors.getAllErrors().iterator();it.hasNext();){
                buffer.append(it.next().getDefaultMessage());
                if(it.hasNext())buffer.append(",");
            }
            return new String(buffer);
        }else{
            return null;
        }
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody @Valid T record, Errors errors){
        checkPermissions("add");
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(getValidateErrors(errors));
        }
        T result= this.getService().save(record);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody @Valid T record,Errors errors){
        checkPermissions("edit");
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(getValidateErrors(errors));
        }
        T result = this.getService().update(record);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity deleteByPrimaryKey(@PathVariable("id") P id){
        checkPermissions("delete");
        int result = this.getService().deleteByPrimaryKey(id);
        if(result>0) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}
