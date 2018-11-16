package com.github.jamsa.rap.meta.model;

public class RapMetaModelStatusField extends RapMetaViewField {
    //private Integer fieldId;
    private boolean hidden;
    private boolean readonly;
    private boolean required;

    private String statusCode;
    private boolean defaultStatus;
    private Long modelStatusId;

    /*@Override
    public Integer getPrimaryKey() {
        return this.fieldId;
    }

    @Override
    public void setPrimaryKey(Integer primaryKey) {
        this.fieldId=primaryKey;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }*/

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(boolean defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public Long getModelStatusId() {
        return modelStatusId;
    }

    public void setModelStatusId(Long modelStatusId) {
        this.modelStatusId = modelStatusId;
    }

    public void apply(RapMetaViewField f) {

    }
}
