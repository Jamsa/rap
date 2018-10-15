package com.github.jamsa.rap.meta.model;

import com.github.jamsa.rap.core.model.BaseEntity;

import java.util.HashMap;
import java.util.Map;

public class RapMetaTable extends BaseEntity<Integer> {
    private Long appId;
    private Long tableId;
    private String tableName;
    private String tableCode;
    private String tableSchema;
    private RapMetaTableField keyField;
    private Map<String,RapMetaTableField> tableFields = new HashMap();
    //private Map<Long,RapMetaTableField> tableFieldMap = new HashMap();

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.firePropertyChange("tableName",tableName);
        this.tableName = tableName;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.firePropertyChange("appId",appId);
        this.appId = appId;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.firePropertyChange("tableCode",tableCode);
        this.tableCode = tableCode;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.firePropertyChange("tableSchema",tableSchema);
        this.tableSchema = tableSchema;
    }

    public Map<String, RapMetaTableField> getTableFields() {
        return tableFields;
    }

    /*public void setTableFields(Map<String, RapMetaTableField> tableFields) {
        this.tableFields = tableFields;
    }*/
    public void addTableField(RapMetaTableField tableField){
        this.tableFields.put(tableField.getFieldCode(),tableField);
        //this.tableFieldMap.put(tableField.getFieldId(),tableField);
        tableField.setTable(this);
        if(tableField.isKeyField()) keyField = tableField;
    }

    @Override
    public Integer getPrimaryKey() {
        return tableId.intValue();
    }

    @Override
    public void setPrimaryKey(Integer primaryKey) {
        this.setTableId(primaryKey.longValue());
    }
}
