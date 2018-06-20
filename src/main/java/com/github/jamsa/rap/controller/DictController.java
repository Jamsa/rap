package com.github.jamsa.rap.controller;

import com.github.jamsa.rap.model.DictType;
import com.github.jamsa.rap.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.jamsa.rap.core.controller.BaseEntityController;
import com.github.jamsa.rap.core.service.BaseEntityService;
import com.github.jamsa.rap.model.Resource;
import com.github.jamsa.rap.service.ResourceService;


/**
 * Created by zhujie on 16/7/5.
 */
@RestController
@RequestMapping("/dict")
public class DictController extends BaseEntityController<DictType,Integer> {
    @Autowired
    DictTypeService dictTypeService;

    @Override
    public BaseEntityService<DictType, Integer> getService() {
        return dictTypeService;
    }

}
