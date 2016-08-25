package com.github.jamsa.rap.core.mapper;

import java.util.List;

/**
 * Created by zhujie on 16/8/23.
 */
public interface BaseMapper<M,P> {

    int deleteByPrimaryKey(P id);


    int insert(M record);


    int insertSelective(M record);


    M selectByPrimaryKey(P id);


    int updateByPrimaryKeySelective(M record);


    int updateByPrimaryKey(M record);

    List<M> selectByCondition(M record);
}
