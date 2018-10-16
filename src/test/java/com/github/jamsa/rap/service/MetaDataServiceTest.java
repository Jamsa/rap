package com.github.jamsa.rap.service;

import com.github.jamsa.rap.meta.model.RapMetaModel;
import com.github.jamsa.rap.meta.service.MetaDataService;

import com.github.jamsa.rap.meta.service.MetaModelService;
import com.github.jamsa.rap.meta.service.MetaService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class MetaDataServiceTest extends BaseTest {
    @Autowired
    MetaDataService metaDataService;

    @Autowired
    MetaService metaService;

    @Test
    public void findMetaModel(){
        RapMetaModel rapMetaModel = metaDataService.findMetaModel("RAP_LOV");
        System.out.println(rapMetaModel);
    }

    @Test
    public void findByPrimaryKey() {
        MetaModelService metaModelService = metaService.getMetaModelService("RAP_LOV");
        Map result = metaModelService.findModelRecordByKey("rapUserLov");
        System.out.println(result);
    }

    @Test
    public void findByPage() {
        MetaModelService metaModelService = metaService.getMetaModelService("RAP_LOV");
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(10);
        pageInfo.setPageNum(1);
        Map condition = new HashMap();
        //condition.put("lovCode","rapUserLov");
        PageInfo result = metaModelService.findModelRecordByPage(condition,pageInfo);
        System.out.println(result);
    }

    @Test
    public void save() {
        MetaModelService metaModelService = metaService.getMetaModelService("RAP_LOV");
        Map record = new HashMap();
        record.put("lovCode","aaaa");
        record.put("lovName","aaaa");

        record = metaModelService.saveModelRecord(record);
        System.out.println(record);
    }

    @Test
    public void update() {
        MetaModelService metaModelService = metaService.getMetaModelService("RAP_LOV");
        Map record = new HashMap();
        record.put("lovCode","aaaa");
        record.put("lovName","aaaabbbbb");

        record = metaModelService.updateModelRecord(record);
        System.out.println(record);
    }

    @Test
    public void saveAndDelete() {
        MetaModelService metaModelService = metaService.getMetaModelService("RAP_LOV");

        this.save();
        int count = metaModelService.deleteModelRecordByKey("aaaa");
        System.out.println(count);

        this.save();
        Map record = new HashMap();
        record.put("lovCode","aaaa");
        count = metaModelService.deleteModelRecord(record);
        System.out.println(count);

    }
}
