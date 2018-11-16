package com.github.jamsa.rap.meta.mapper;


import com.github.jamsa.rap.meta.model.RapMetaModelStatusField;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * 元数据Mapper
 */
@Mapper
public interface RapMetaDataMapper{

    /**
     * 查询模型状态字段
     * @param modelCode 模型代码
     * @return
     */
    List<RapMetaModelStatusField> selectStatusFieldsByModelId(Long modelId);

    List<RapMetaModelStatusField> selectEmptyStatusFieldsByModelId(Long modelId);
}