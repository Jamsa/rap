package com.github.jamsa.rap.meta.model;

public class RapMetaModelViewObject extends RapMetaViewObject{
    private ModelViewObjectType viewType;
    private boolean updatable=true;
    private boolean deletable=true;
    private boolean creatable=true;
    private Long relFieldId;
    private Long modelId;
    private String viewAlias;

    private RapMetaViewField relField;
    private RapMetaModel model;

    @Override
    public void addViewField(RapMetaViewField viewField) {
        super.addViewField(viewField);
        if(viewField.getFieldId().equals(relFieldId)) this.relField = viewField;
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

    public RapMetaViewField getRelField() {
        return relField;
    }

    public void setRelField(RapMetaViewField relField) {
        this.relField = relField;
    }

    public Long getRelFieldId() {
        return relFieldId;
    }

    public void setRelFieldId(Long relFieldId) {
        this.firePropertyChange("relFieldId", relFieldId);
        this.relFieldId = relFieldId;
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
