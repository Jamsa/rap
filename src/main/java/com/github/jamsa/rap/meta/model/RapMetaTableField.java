package com.github.jamsa.rap.meta.model;

import com.github.jamsa.rap.core.model.BaseEntity;

public class RapMetaTableField extends BaseEntity<Integer> {
    private Long fieldId;
    private Long tableId;
    private String fieldName;
    private String fieldCode;
    private String fieldType;
    private boolean required;
    private boolean uniq;
    private boolean keyField;

    private Integer fieldLength;
    private Integer fieldPrecision;
    private RapMetaTable table;

    public RapMetaTable getTable() {
        return table;
    }

    public void setTable(RapMetaTable table) {
        this.table = table;
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

    public boolean isKeyField() {
        return keyField;
    }

    public void setKeyField(boolean keyField) {
        this.firePropertyChange("keyField",keyField);
        this.keyField = keyField;
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

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.firePropertyChange("tableId",tableId);
        this.tableId = tableId;
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
