package com.github.jamsa.rap.service;

import com.github.jamsa.rap.mapper.DictTypeMapper;
import com.github.jamsa.rap.model.DictType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jamsa.rap.core.mapper.BaseMapper;
import com.github.jamsa.rap.core.service.BaseEntityService;
import com.github.jamsa.rap.mapper.ResourceMapper;
import com.github.jamsa.rap.model.Resource;


/**
 * Created by zhujie on 16/7/5.
 */
@Component
public class DictTypeService extends BaseEntityService<DictType,Integer> {
    @Autowired
    DictTypeMapper dictTypeMapper;

    @Override
    public BaseMapper<DictType, Integer> getMapper() {
        return this.dictTypeMapper;
    }

}
