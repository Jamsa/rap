package com.github.jamsa.rap.meta.model;

public class RapMetaModelStatusField extends RapMetaViewField {
    private Long fieldId;
    private String fieldCode;
    private boolean hidden;
    private boolean readonly;
    private boolean required;

    @Override
    public Long getFieldId() {
        return fieldId;
    }

    @Override
    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public String getFieldCode() {
        return fieldCode;
    }

    @Override
    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

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
}
