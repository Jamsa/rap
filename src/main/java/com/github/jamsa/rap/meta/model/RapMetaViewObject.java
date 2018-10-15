package com.github.jamsa.rap.meta.model;

import com.github.jamsa.rap.core.model.BaseEntity;

import java.util.HashMap;
import java.util.Map;

public class RapMetaViewObject  extends BaseEntity<Integer> {
    private Long viewObjectId;
    private Long tableId;
    private String objectName;
    private String objectCode;
    private String objectSql;
    //private Map<String,RapMetaViewField> objectFields = new HashMap();
    private RapMetaTable table;
    //private Long keyFieldId;
    private RapMetaViewField keyField;

    private Map<String,RapMetaViewField> viewFields = new HashMap(); // <别名, 字段>

    public Map<String, RapMetaViewField> getViewFields() {
        return viewFields;
    }

    public RapMetaViewField getKeyField() {
        return keyField;
    }

    /*public void setViewFields(Map<String, RapMetaViewField> viewFields) {
        this.viewFields = viewFields;
    }*/

    public void addViewField(RapMetaViewField viewField){
        viewField.setViewObject(this);
        this.viewFields.put(viewField.getFieldAlias(),viewField);
        //if(viewField.getTableField()!=null&&viewField.getTableField().isKeyField()) keyField = viewField;
        //if(viewField.getFieldId()!=null&&viewField.getFieldId().equals(keyFieldId)) keyField=viewField;
        if(viewField.isKeyField())keyField=viewField;
    }

    public RapMetaTable getTable() {
        return table;
    }

    public void setTable(RapMetaTable table) {
        this.table = table;
    }

    public Long getViewObjectId() {
        return viewObjectId;
    }

    public void setViewObjectId(Long viewObjectId) {
        this.firePropertyChange("viewObjectId",viewObjectId);
        this.viewObjectId = viewObjectId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.firePropertyChange("tableId",tableId);
        this.tableId = tableId;
    }

    /*
    public Long getKeyFieldId() {
        return keyFieldId;
    }

    public void setKeyFieldId(Long keyFieldId) {
        this.firePropertyChange("keyFieldId",keyFieldId);
        this.keyFieldId = keyFieldId;
    }


    public Map<String, RapMetaViewField> getObjectFields() {
        return objectFields;
    }

    public void setObjectFields(Map<String, RapMetaViewField> objectFields) {
        this.objectFields = objectFields;
    }*/

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.firePropertyChange("objectName",objectName);
        this.objectName = objectName;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.firePropertyChange("objectCode",objectCode);
        this.objectCode = objectCode;
    }

    public String getObjectSql() {
        return objectSql;
    }

    public void setObjectSql(String objectSql) {
        this.firePropertyChange("objectSql", objectSql);
        this.objectSql = objectSql;
    }

    @Override
    public Integer getPrimaryKey() {
        return this.viewObjectId.intValue();
    }

    @Override
    public void setPrimaryKey(Integer primaryKey) {
        this.setViewObjectId(primaryKey.longValue());
    }
}
