package com.github.jamsa.rap.core.service;

import com.github.jamsa.rap.core.mapper.BaseMapper;
import com.github.jamsa.rap.core.model.BaseEntity;

import java.io.Serializable;

/**
 * Created by zhujie on 16/8/23.
 */
public abstract class BaseService<T extends BaseEntity<P>,P extends Serializable> {

    public abstract BaseMapper<T,P> getMapper();

    public int deleteByPrimaryKey(P id){
        return this.getMapper().deleteByPrimaryKey(id);
    }

    public int save(T record){
        return this.getMapper().insert(record);
    }

    public T findByPrimaryKey(P id){
        return this.getMapper().selectByPrimaryKey(id);
    }

    public int update(T record){
        return this.getMapper().updateByPrimaryKeySelective(record);
    }

    public int delete(T record){
        return this.getMapper().deleteByPrimaryKey(record.getPrimaryKey());
    }

}
