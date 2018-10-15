package com.github.jamsa.rap.meta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 元服务，用于构造基于元数据的动态创建的服务对象
 */
@Component
public class MetaService {

    @Autowired
    private MetaDataService metaDataService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 存放各个模块的服务对象
     */
    private Map<String,MetaModelService> metaModelServices = new ConcurrentHashMap<>();

    public MetaModelService getMetaModelService(String modelCode){
        MetaModelService metaModelService = metaModelServices.get(modelCode);
        if(metaModelService==null){
            metaModelService = new MetaModelService(metaDataService.findMetaModel(modelCode),jdbcTemplate);
            if(metaModelService!=null)
                metaModelServices.put(modelCode,metaModelService);
        }
        return metaModelService;
    }
}
