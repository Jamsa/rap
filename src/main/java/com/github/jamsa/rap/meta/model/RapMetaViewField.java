package com.github.jamsa.rap.meta.model;

import com.github.jamsa.rap.core.model.BaseEntity;

import java.util.HashMap;
import java.util.Map;

public class RapMetaViewField  extends BaseEntity<Integer> {
    private Long fieldId;
    private String fieldName;
    private String fieldCode;
    private String fieldAlias;
    private String fieldType;
    private Long tableFieldId;
    private Long viewObjectId;
    private boolean keyField;
    private Map<String,String> fieldProps = new HashMap();
    private RapMetaTableField tableField;
    private RapMetaViewObject viewObject;


    public String getFieldAlias() {
        return fieldAlias;
    }

    public void setFieldAlias(String fieldAlias) {
        this.firePropertyChange("fieldAlias",fieldAlias);
        this.fieldAlias = fieldAlias;
    }

    public boolean isKeyField() {
        return keyField;
    }

    public void setKeyField(boolean keyField) {
        this.firePropertyChange("keyField",keyField);
        this.keyField = keyField;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.firePropertyChange("fieldName",fieldName);
        this.fieldName = fieldName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.firePropertyChange("fieldCode",fieldCode);
        this.fieldCode = fieldCode;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.firePropertyChange("fieldType",fieldType);
        this.fieldType = fieldType;
    }

    public Map<String, String> getFieldProps() {
        return fieldProps;
    }

    public void setFieldProps(Map<String, String> fieldProps) {
        this.fieldProps = fieldProps;
    }

    public RapMetaTableField getTableField() {
        return tableField;
    }

    public void setTableField(RapMetaTableField tableField) {
        this.tableField = tableField;
    }

    public Long getTableFieldId() {
        return tableFieldId;
    }

    public void setTableFieldId(Long tableFieldId) {
        this.firePropertyChange("tableFieldId",tableFieldId);
        this.tableFieldId = tableFieldId;
    }

    public Long getViewObjectId() {
        return viewObjectId;
    }

    public void setViewObjectId(Long viewObjectId) {
        this.firePropertyChange("viewObjectId",viewObjectId);
        this.viewObjectId = viewObjectId;
    }

    public RapMetaViewObject getViewObject() {
        return viewObject;
    }

    public void setViewObject(RapMetaViewObject viewObject) {
        this.viewObject = viewObject;
    }

    @Override
    public Integer getPrimaryKey() {
        return this.fieldId.intValue();
    }

    @Override
    public void setPrimaryKey(Integer primaryKey) {
        this.setFieldId(primaryKey.longValue());
    }
}
