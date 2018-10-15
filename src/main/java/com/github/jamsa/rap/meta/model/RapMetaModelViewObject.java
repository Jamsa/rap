package com.github.jamsa.rap.meta.model;

public class RapMetaModelViewObject extends RapMetaViewObject{
    private ModelViewObjectType viewType;
    private boolean updatable=true;
    private boolean deletable=true;
    private boolean creatable=true;
    private Long refFieldId;
    private Long modelId;
    private String viewAlias;
    private RapMetaViewField refField;
    private RapMetaModel model;

    @Override
    public void addViewField(RapMetaViewField viewField) {
        super.addViewField(viewField);
        if(viewField.getFieldId().equals(refFieldId)) this.refField = viewField;
    }

    public String getViewAlias() {
        return viewAlias;
    }

    public void setViewAlias(String viewAlias) {
        this.firePropertyChange("viewAlias",viewAlias);
        this.viewAlias = viewAlias;
    }

    public ModelViewObjectType getViewType() {
        return viewType;
    }

    public void setViewType(ModelViewObjectType viewType) {
        this.firePropertyChange("viewType",viewType);
        this.viewType = viewType;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.firePropertyChange("updatable",updatable);
        this.updatable = updatable;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.firePropertyChange("deletable",deletable);
        this.deletable = deletable;
    }

    public boolean isCreatable() {
        return creatable;
    }

    public void setCreatable(boolean creatable) {
        this.firePropertyChange("creatable",creatable);
        this.creatable = creatable;
    }

    public RapMetaViewField getRefField() {
        return refField;
    }

    public void setRefField(RapMetaViewField refField) {
        this.refField = refField;
    }

    public Long getRefFieldId() {
        return refFieldId;
    }

    public void setRefFieldId(Long refFieldId) {
        this.firePropertyChange("refFieldId",refFieldId);
        this.refFieldId = refFieldId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.firePropertyChange("modelId",modelId);
        this.modelId = modelId;
    }

    public RapMetaModel getModel() {
        return model;
    }

    public void setModel(RapMetaModel model) {
        this.model = model;
    }
}
