package com.github.jamsa.rap.service;

import com.github.jamsa.rap.mapper.DictTypeMapper;
import com.github.jamsa.rap.model.DictType;
import com.github.jamsa.rap.model.User;
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

    public int deleteByPrimaryKey(Integer id){
        dictTypeMapper.deleteTypeAllValues(id);
        return this.getMapper().deleteByPrimaryKey(id);
    }

    public DictType save(DictType record){
        //保存主表
        this.getMapper().insert(record);
        if(!record.getValues().isEmpty())
            dictTypeMapper.insertTypeValues(record.getDictTypeId(),record.getValues());
        return record;
    }

    public DictType update(DictType record){
        this.getMapper().updateByPrimaryKeySelective(record);
        dictTypeMapper.deleteTypeAllValues(record.getDictTypeId());
        if(!record.getValues().isEmpty())
            dictTypeMapper.insertTypeValues(record.getDictTypeId(),record.getValues());
        return record;
    }

}
