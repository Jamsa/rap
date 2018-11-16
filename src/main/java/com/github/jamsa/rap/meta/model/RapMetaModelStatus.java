package com.github.jamsa.rap.meta.model;

import java.util.HashMap;
import java.util.Map;

public class RapMetaModelStatus{
    private Long modelStatusId;
    private String statusCode;
    private boolean defaultStatus;

    private Map<String,RapMetaModelStatusField> modelStatusFields = new HashMap();

    public Long getModelStatusId() {
        return modelStatusId;
    }

    public void setModelStatusId(Long modelStatusId) {
        this.modelStatusId = modelStatusId;
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

    public Map<String, RapMetaModelStatusField> getModelStatusFields() {
        return modelStatusFields;
    }

    /*public void setModelStatusFields(Map<String, RapMetaModelStatusField> modelStatusFields) {
        this.modelStatusFields = modelStatusFields;
    }*/
    public void addModelStatusField(RapMetaModelStatusField modelStatusField) {
        this.modelStatusFields.put(modelStatusField.getFieldAlias(),modelStatusField);
    }
}
