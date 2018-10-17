package com.github.jamsa.rap.meta.service;

import com.github.jamsa.rap.meta.model.RapMetaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
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
        return getMetaModelService(modelCode,MetaModelService.class);
    }

    public MetaModelService getMetaModelService(String modelCode,Class clazz){

        MetaModelService metaModelService = metaModelServices.get(modelCode);

        if(metaModelService==null){
            try {
                Constructor<MetaModelService> constructor = clazz.getConstructor(RapMetaModel.class, JdbcTemplate.class);
                metaModelService = constructor.newInstance(metaDataService.findMetaModel(modelCode), jdbcTemplate);
            }catch (Exception e){
                e.printStackTrace();

                //如果不是MataModelService则加载默认的
                if(!clazz.equals(MetaModelService.class)) {
                    metaModelService = new MetaModelService(metaDataService.findMetaModel(modelCode), jdbcTemplate);
                }
            }

            if(metaModelService!=null)
                metaModelServices.put(modelCode,metaModelService);
        }
        return metaModelService;
    }



    public void removeMetaModelService(String modelCode){
        metaModelServices.remove(modelCode);
    }
}
