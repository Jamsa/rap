package com.github.jamsa.rap.meta.controller;

import com.github.jamsa.rap.meta.model.RapMetaModel;
import com.github.jamsa.rap.meta.service.MetaModelService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.AuthorizationException;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 元模块控制器，由MetaController实例化
 */
public class MetaModelController {
    private MetaModelControllerListener metaModelControllerListener;
    private String modelCode;
    private MetaModelService metaModelService;
    private RapMetaModel metaModel;

    private static Pattern pathPattern = Pattern.compile("/(\\w*)");
    public static void main(String[] args) {
        Matcher m = pathPattern.matcher("/aaa/bb/ccc/ddd?kk=1&bb=2");
        while (m.find()){
            System.out.println(m.group(1));
        }
    }

    public MetaModelController(String modelCode,MetaModelService metaModelService) {
        this.metaModelService = metaModelService;
        this.modelCode = modelCode;
        this.metaModel = metaModelService.getMetaModel();
    }

    private String[] getUriSegments(HttpServletRequest request){
        List<String> result = new ArrayList();
        String uri = request.getRequestURI();
        uri = uri.replaceFirst(request.getContextPath()+"/meta-api","");
        Matcher m = pathPattern.matcher(uri);
        while (m.find()){
            result.add(m.group(1));
        }
        return result.toArray(new String[result.size()]);
    }

    public Object dispatch(HttpServletRequest request){
        String requestMetod = request.getMethod();

        String[] uriSegments = getUriSegments(request);
        if(uriSegments.length>0 && modelCode.equals(uriSegments[0])) {
            if (uriSegments.length==1) {
                if (HttpMethod.GET.name().equals(requestMetod)) {
                    return findByPage(request);
                }

                if (HttpMethod.POST.name().equals(requestMetod)) {
                    return save(request);
                }

                if (HttpMethod.PUT.name().equals(requestMetod)) {
                    return update(request);
                }
            }

            if (uriSegments.length==2) {
                String seg2 = uriSegments[1];
                if(metaModel.isSubTableViewAlias(seg2)){ //子表记录查询

                    if (HttpMethod.GET.name().equals(requestMetod)) {
                        return findByPage(request,seg2);
                    }
                }else {
                    //主表记录查询
                    if (HttpMethod.GET.name().equals(requestMetod)) {
                        return findByPrimaryKey(request,seg2);
                    }

                    if (HttpMethod.DELETE.name().equals(requestMetod)) {
                        return deleteByPrimaryKey(request,seg2);
                    }
                }

            }
        }

        return unknowRequest(request);
    }

    protected void checkPermissions(String permissions){

        /*String permission = "meta-"+modelCode+":"+permissions;
        if(!SecurityUtils.getSubject().isPermitted(permission))
            throw new AuthorizationException("没有权限访问目标资源!");*/
    }

    public ResponseEntity unknowRequest(HttpServletRequest request){
        throw new AuthorizationException("目标资源不存在!");
    }

    //@RequestMapping(value = "",method = {RequestMethod.GET})
    public ResponseEntity findByPage(HttpServletRequest request){
        return findByPage(request,null);
    }

    protected Map<String,String> getParameterMap(HttpServletRequest request){
        return request.getParameterMap().entrySet().stream()
                .collect(()->new HashMap<String,String>(),
                        (m,e)->m.put(e.getKey(),String.join(",",e.getValue())),
                        (m1,m2)->m1.putAll(m2));
    }

    public ResponseEntity findByPage(HttpServletRequest request,String viewAlias){
        checkPermissions("view");
        Map condition = getParameterMap(request);
        PageInfo page = new PageInfo();

        if(page.getPageSize()==0){
            page.setPageSize(10);
        }
        if(page.getPageNum()==0){
            page.setPageNum(1);
        }

        PageInfo<Map> result = null;
        if(StringUtils.isEmpty(viewAlias)) result = metaModelService.findModelRecordByPage(condition,page);
        else result = metaModelService.findByPage(viewAlias,condition,page);

        return ResponseEntity.ok(result);
    }

    //@RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity findByPrimaryKey(HttpServletRequest request,String id){
        checkPermissions("view");
        Object result = metaModelService.findModelRecordByKey(id);
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
    public ResponseEntity save(HttpServletRequest request){
        checkPermissions("add");
        //TODO
        Map record = new HashMap();

        Object result= metaModelService.saveModelRecord(record);
        return ResponseEntity.ok(result);
    }

    //@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseEntity update(HttpServletRequest request){
        checkPermissions("edit");
        //TODO
        Map record = new HashMap();
        Object result = metaModelService.updateModelRecord(record);
        return ResponseEntity.ok(result);
    }

    //@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity deleteByPrimaryKey(HttpServletRequest request,String id){
        checkPermissions("delete");
        int result = metaModelService.deleteModelRecordByKey(id);
        if(result>0) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}