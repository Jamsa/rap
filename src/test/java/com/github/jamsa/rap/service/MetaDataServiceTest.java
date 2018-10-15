package com.github.jamsa.rap.service;

import com.github.jamsa.rap.meta.model.RapMetaModel;
import com.github.jamsa.rap.meta.service.MetaDataService;

import com.github.jamsa.rap.meta.service.MetaModelService;
import com.github.jamsa.rap.meta.service.MetaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class MetaDataServiceTest extends BaseTest {
    @Autowired
    MetaDataService metaDataService;

    @Autowired
    MetaService metaService;

    @Test
    public void findModelByCode() {
        RapMetaModel rapMetaModel = metaDataService.findMetaModel("RAP_LOV");
        MetaModelService metaModelService = metaService.getMetaModelService("RAP_LOV");
        Map result = metaModelService.findByPrimaryKey("rapUserLov");
        System.out.println(result);
    }
}
