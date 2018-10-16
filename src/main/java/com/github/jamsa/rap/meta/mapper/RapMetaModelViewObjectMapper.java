package com.github.jamsa.rap.meta.mapper;

import com.github.jamsa.rap.core.mapper.BaseMapper;
import com.github.jamsa.rap.meta.model.RapMetaModelViewObject;
import com.github.jamsa.rap.meta.model.RapMetaModelViewObjectKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RapMetaModelViewObjectMapper extends BaseMapper<RapMetaModelViewObject, Integer> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RAP_META_MODEL_OBJECT
     *
     * @mbggenerated Sat Oct 13 19:35:54 CST 2018
     */
    int deleteByPrimaryKey(RapMetaModelViewObjectKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RAP_META_MODEL_OBJECT
     *
     * @mbggenerated Sat Oct 13 19:35:54 CST 2018
     */
    int insert(RapMetaModelViewObject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RAP_META_MODEL_OBJECT
     *
     * @mbggenerated Sat Oct 13 19:35:54 CST 2018
     */
    int insertSelective(RapMetaModelViewObject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RAP_META_MODEL_OBJECT
     *
     * @mbggenerated Sat Oct 13 19:35:54 CST 2018
     */
    RapMetaModelViewObject selectByPrimaryKey(RapMetaModelViewObjectKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RAP_META_MODEL_OBJECT
     *
     * @mbggenerated Sat Oct 13 19:35:54 CST 2018
     */
    int updateByPrimaryKeySelective(RapMetaModelViewObject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table RAP_META_MODEL_OBJECT
     *
     * @mbggenerated Sat Oct 13 19:35:54 CST 2018
     */
    int updateByPrimaryKey(RapMetaModelViewObject record);

    /** @mbggenerated */
    List<RapMetaModelViewObject> selectByCondition(RapMetaModelViewObject condition);

    List<RapMetaModelViewObject> selectByModelId(Long modelId);

}