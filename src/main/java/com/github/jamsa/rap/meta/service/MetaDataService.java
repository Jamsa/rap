package com.github.jamsa.rap.meta.service;

import com.github.jamsa.rap.meta.mapper.*;
import com.github.jamsa.rap.meta.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 元数据服务，用于获取各种元数据
 */
@Component
public class MetaDataService {

    @Autowired
    private RapMetaModelMapper rapMetaModelMapper;

    @Autowired
    private RapMetaModelViewObjectMapper rapMetaModelViewObjectMapper;

    @Autowired
    private RapMetaViewFieldMapper rapMetaViewFieldMapper;

    @Autowired
    private RapMetaDataMapper rapMetaDataMapper;

    //@Autowired
    //private RapMetaTableMapper rapMetaTableMapper;

    //@Autowired
    //private RapMetaTableFieldMapper rapMetaTableFieldMapper;

    public RapMetaModel findMetaModel(String modelCode){
        RapMetaModel model = rapMetaModelMapper.selectByModelCode(modelCode);
        Long modelId = model.getModelId();

        /*Map<Long,RapMetaTable> tables = rapMetaTableMapper.selectByModelId(modelId).stream()
                .collect(HashMap::new,(m,t)->m.put(t.getTableId(),t),HashMap::putAll);
        Map<Long,RapMetaTableField> tableFields = rapMetaTableFieldMapper.selectByModelId(modelId).stream()
                .collect(HashMap::new,(m, f)->m.put(f.getFieldId(),f),HashMap::putAll);*/

        Map<Long,RapMetaModelViewObject> viewObjects = rapMetaModelViewObjectMapper.selectByModelId(modelId).stream()
                .collect(HashMap::new,(m,v)->m.put(v.getViewObjectId(),v),HashMap::putAll);
        Map<Long,RapMetaViewField> viewFields = rapMetaViewFieldMapper.selectByModelId(modelId).stream()
                .collect(HashMap::new,(m, f)->m.put(f.getFieldId(),f),HashMap::putAll);

        /*tableFields.values().forEach(f->{
            RapMetaTable table = tables.get(f.getTableId());
            table.addTableField(f);
        });*/

        viewFields.values().forEach(f->{
            //f.setTableField(tableFields.get(f.getTableFieldId()));
            RapMetaModelViewObject viewObject = viewObjects.get(f.getViewObjectId());
            viewObject.addViewField(f);
        });

        viewObjects.values().forEach(v->{
            //Long tableId = v.getTableId();
            //if(tableId!=null) v.setTable(tables.get(tableId));
            model.addModelViewObject(v);
        });

        List<RapMetaModelStatusField> statusFields = rapMetaDataMapper.selectStatusFieldsByModelId(modelId);

        if(statusFields==null || statusFields.isEmpty() || !statusFields.stream().anyMatch(f->f.isDefaultStatus())){
            statusFields.addAll(rapMetaDataMapper.selectEmptyStatusFieldsByModelId(modelId));
        }
        statusFields.forEach(sf->{
            RapMetaModelStatus status = model.getModelStatus().get(sf.getStatusCode());
            if(status==null){
                status = new RapMetaModelStatus();
                status.setDefaultStatus(sf.isDefaultStatus());
                status.setStatusCode(sf.getStatusCode());
                status.setModelStatusId(sf.getModelStatusId());
                model.addModelStatus(status);
            }
            status.addModelStatusField(sf);
        });

        return model;
    }
}
