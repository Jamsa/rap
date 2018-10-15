package com.github.jamsa.rap.meta.controller;

import com.github.jamsa.rap.meta.service.MetaDataService;
import com.github.jamsa.rap.meta.service.MetaModelService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 元模块控制器，由MetaController实例化
 */
public class MetaModelController {
    private MetaModelControllerListener metaModelControllerListener;
    private String modelCode;
    private MetaModelService metaModelService;
    private Pattern modelRootPattern;
    private Pattern modelDetailPattern;

    public MetaModelController(ServletContext servletContext, String modelCode, MetaDataService metaDataService, MetaModelService metaModelService) {
        this.metaModelService = metaModelService;//new MetaModelService(modelCode,metaDataService);
        this.modelCode = modelCode;
        String contextPath = servletContext.getContextPath();
        modelRootPattern = Pattern.compile(contextPath+"/"+modelCode);
        modelDetailPattern =  Pattern.compile(contextPath+ "/"+modelCode+"/(.*+)");
    }

    public Object dispatch(HttpServletRequest request){
        String requestMetod = request.getMethod();

        String uri = request.getRequestURI();
        Matcher modelRootMatcher  = modelRootPattern.matcher(uri);
        Matcher modelDetailMatcher = modelDetailPattern.matcher(uri);
        String detailId = modelDetailMatcher.group(1);

        if(modelRootMatcher.matches()){
            if(HttpMethod.GET.name().equals(requestMetod)){
                return findByPage();
            }

            if(HttpMethod.POST.name().equals(requestMetod)){
                return save();
            }

            if(HttpMethod.PUT.name().equals(requestMetod)){
                return update();
            }
        }

        if(modelDetailMatcher.matches() && !StringUtils.isEmpty(detailId)){

            if(HttpMethod.GET.name().equals(requestMetod)){
                return findByPrimaryKey(detailId);
            }

            if(HttpMethod.DELETE.name().equals(requestMetod)){
                return deleteByPrimaryKey(detailId);
            }

        }

        return unknowRequest();
    }

    protected void checkPermissions(String permissions){
        String permission = "meta-"+modelCode+":"+permissions;
        if(!SecurityUtils.getSubject().isPermitted(permission))
            throw new AuthorizationException("没有权限访问目标资源!");
    }

    public ResponseEntity unknowRequest(){
        throw new AuthorizationException("目标资源不存在!");
    }

    //@RequestMapping(value = "",method = {RequestMethod.GET})
    public ResponseEntity findByPage(){
        checkPermissions("view");
        Map condition = new HashMap();
        PageInfo page = new PageInfo();

        if(page.getPageSize()==0){
            page.setPageSize(10);
        }
        if(page.getPageNum()==0){
            page.setPageNum(1);
        }
        PageInfo<Map> result = metaModelService.findByPage(condition,page);
        return ResponseEntity.ok(result);
    }

    //@RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity findByPrimaryKey(String id){
        checkPermissions("view");
        Object result = metaModelService.findByPrimaryKey(id);
        if(result!=null){
            return ResponseEntity.ok(result);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    protected String getValidateErrors(Errors errors){
        if(errors.hasErrors()) {
            StringBuffer buffer = new StringBuffer();
            for(Iterator<ObjectError> it = errors.getAllErrors().iterator(); it.hasNext();){
                buffer.append(it.next().getDefaultMessage());
                if(it.hasNext())buffer.append(",");
            }
            return new String(buffer);
        }else{
            return null;
        }
    }

    //@RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseEntity save(){
        checkPermissions("add");
        //TODO
        Map record = new HashMap();

        Object result= metaModelService.save(record);
        return ResponseEntity.ok(result);
    }

    //@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseEntity update(){
        checkPermissions("edit");
        //TODO
        Map record = new HashMap();
        Object result = metaModelService.update(record);
        return ResponseEntity.ok(result);
    }

    //@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity deleteByPrimaryKey(String id){
        checkPermissions("delete");
        int result = metaModelService.deleteByPrimaryKey(id);
        if(result>0) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
