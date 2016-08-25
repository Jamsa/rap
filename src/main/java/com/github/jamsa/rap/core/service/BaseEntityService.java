package com.github.jamsa.rap.core.service;

import com.github.jamsa.rap.core.mapper.BaseMapper;
import com.github.jamsa.rap.core.model.BaseEntity;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhujie on 16/8/23.
 */
public abstract class BaseEntityService<T extends BaseEntity<P>,P extends Serializable> extends BaseService {

    public abstract BaseMapper<T,P> getMapper();

    public int deleteByPrimaryKey(P id){
        return this.getMapper().deleteByPrimaryKey(id);
    }

    public T save(T record){
        this.getMapper().insert(record);
        return record;
    }

    public T findByPrimaryKey(P id){
        return this.getMapper().selectByPrimaryKey(id);
    }

    public T update(T record){
        this.getMapper().updateByPrimaryKeySelective(record);
        return record;
    }

    public int delete(T record){
        return this.getMapper().deleteByPrimaryKey(record.getPrimaryKey());
    }

    public List<T> findByCondition(T record){
        return this.getMapper().selectByCondition(record);
    }

    public PageInfo<T> findByPage(T record,PageInfo<T> page){
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<T> result = this.getMapper().selectByCondition(record);
        PageInfo<T> pageInfo = new PageInfo<T>(result);
        return pageInfo;
    }

}
