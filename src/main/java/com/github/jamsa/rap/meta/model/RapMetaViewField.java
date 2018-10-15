package com.github.jamsa.rap.meta.model;

import com.github.jamsa.rap.core.model.BaseEntity;

import java.sql.JDBCType;
import java.util.HashMap;
import java.util.Map;

public class RapMetaViewField  extends BaseEntity<Integer> {
    private Long fieldId;
    private String fieldName;
    private String fieldCode;
    private String fieldAlias;

    private JDBCType dataType;
    private ModelViewFieldType fieldType;
    //private Long tableFieldId;
    private Long viewObjectId;
    private boolean keyField;

    private boolean required;
    private boolean uniq;

    private Integer fieldLength;
    private Integer fieldPrecision;

    private Map<String,String> fieldProps = new HashMap();

    private RapMetaViewObject viewObject;


    public String getFieldAlias() {
        return fieldAlias;
    }

    public void setFieldAlias(String fieldAlias) {
        this.firePropertyChange("fieldAlias",fieldAlias);
        this.fieldAlias = fieldAlias;
    }

    public ModelViewFieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(ModelViewFieldType fieldType) {
        this.firePropertyChange("fieldType",fieldType);
        this.fieldType = fieldType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.firePropertyChange("required",required);
        this.required = required;
    }

    public boolean isUniq() {
        return uniq;
    }

    public void setUniq(boolean uniq) {
        this.firePropertyChange("uniq",uniq);
        this.uniq = uniq;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(Integer fieldLength) {
        this.firePropertyChange("fieldLength",fieldLength);
        this.fieldLength = fieldLength;
    }

    public Integer getFieldPrecision() {
        return fieldPrecision;
    }

    public void setFieldPrecision(Integer fieldPrecision) {
        this.firePropertyChange("fieldPrecision",fieldPrecision);
        this.fieldPrecision = fieldPrecision;
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

    public JDBCType getDataType() {
        return dataType;
    }

    public void setDataType(JDBCType dataType) {
        this.firePropertyChange("dataType", dataType);
        this.dataType = dataType;
    }

    public Map<String, String> getFieldProps() {
        return fieldProps;
    }

    public void setFieldProps(Map<String, String> fieldProps) {
        this.fieldProps = fieldProps;
    }

    /*public RapMetaTableField getTableField() {
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
    }*/

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
