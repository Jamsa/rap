package com.github.jamsa.rap.meta.model;

import com.github.jamsa.rap.core.model.BaseEntity;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RapMetaModel  extends BaseEntity<Integer> {
    private Long modelId;
    private Long appId;
    private String modelName;
    private String modelCode;
    private Long statusFieldId;
    private RapMetaViewField statusField;

    private RapMetaModelViewObject mainViewObject;

    private Map<String,RapMetaModelViewObject> modelViewObjects = new HashMap();
    private Map<String,RapMetaModelStatus> modelStatus = new HashMap();

    //默认状态
    private RapMetaModelStatus defaultMetaModelStatus;

    public RapMetaModelStatus getDefaultMetaModelStatus(){
        return defaultMetaModelStatus;
    }

    //todo
    //getStatusFields(statusCode) 获取某个状态下所有字段信息，字段的属性中包含（状态属性）
    //public Map<String,> getDefaultStatusFields()


    protected String getDeleteByMainKeySql(RapMetaModelViewObject v){
        return Optional.ofNullable(v).filter(RapMetaModelViewObject::isDeletable)
                .map(mv->mv.getViewType()==ModelViewObjectType.MAIN?mv.getKeyField():mv.getRefField())
                .map(RapMetaViewField::getTableField)
                .map(tf->"delete from "+tf.getTable().getTableCode()+" where "+tf.getFieldCode()+"=?").orElse(null);


        /*RapMetaViewField keyField = v.getViewType()==ModelViewObjectType.MAIN?v.getKeyField():v.getRefField();

        if(keyField!=null &&
                keyField.getTableField()!=null &&
                v.isDeletable() &&
                v.getTable()!=null &&
                v.getRefField()!=null &&
                v.getRefField().getTableField()!=null){
            return "delete from "+v.getTable().getTableCode()+" where "+keyField.getTableField().getFieldCode()+"=?";
        }
        return null;*/
    }

    //private String[] deleteByPrimaryKeySqls=null;
    public List<SqlAndParamValues> getDeleteByMainKeySqls(Object id){
        return getModelViewObjects().values().stream().sorted(Comparator.comparing(RapMetaModelViewObject::getViewType).reversed())
                .map(this::getDeleteByMainKeySql)
                .filter(StringUtils::isEmpty)
                .map(s->new SqlAndParamValues(s,new Object[]{id})).collect(Collectors.toList());

        /*Collection<RapMetaModelViewObject> viewObjects = this.getModelViewObjects().values();
        return Arrays.asList(Constant.MODEL_VIEW_OBJECT_TYPE_SUBTABLE,Constant.MODEL_VIEW_OBJECT_TYPE_ADDITIONAL,Constant.MODEL_VIEW_OBJECT_TYPE_MAIN)
                .stream().flatMap(viewType->viewObjects.stream().filter(v->v.getViewType().equals(viewType)))
                .map(v->getDeleteByMainKeySql(v))
                .filter(s->!StringUtils.isEmpty(s)).map(s->new SqlAndParamValues(s,new Object[]{id})).collect(Collectors.toList());*/
    }

    public SqlAndParamValues getDeleteSql(RapMetaModelViewObject v, Object id){
        return Optional.ofNullable(v).filter(RapMetaModelViewObject::isDeletable)
                .map(RapMetaViewObject::getKeyField)
                .map(RapMetaViewField::getTableField)
                .map(tf->new SqlAndParamValues("delete from "+tf.getTable().getTableCode()+" where "+tf.getFieldCode()+"=?",new Object[]{id}))
                .orElse(null);
    }

    public SqlAndParamValues getSaveSql(RapMetaModelViewObject v,Map record){

        List<String> fieldNames = new ArrayList();
        List<String> fieldParams = new ArrayList();
        List<Object> fieldValues = new ArrayList();
        if(v.getTable()!=null&&v.isCreatable()){
            // 非主键的表字段
            v.getViewFields().values().stream().filter(f->f.getTableField()!=null && !f.getTableField().isKeyField()).forEach(f->{
                fieldNames.add(f.getTableField().getFieldCode());
                fieldParams.add("?");
                fieldValues.add(record.get(f.getFieldAlias()));
            });
            StringBuffer buf = new StringBuffer();
            buf.append("insert into ").append(v.getTable().getTableCode())
                    .append("(").append(String.join(",",fieldNames))
                    .append(") values(")
                    .append(String.join(",",fieldParams))
                    .append(")");
            return new SqlAndParamValues(buf.toString(),fieldValues.toArray());
        }
        return null;
    }

    public SqlAndParamValues getUpdateSql(RapMetaModelViewObject v,Map record){
        List<String> fieldNames = new ArrayList();
        List<Object> fieldValues = new ArrayList();
        if(v.getTable()!=null&&v.isUpdatable()){
            // 非主键的表字段
            v.getViewFields().values().stream().filter(f->f.getTableField()!=null && !f.getTableField().isKeyField()).forEach(f->{
                fieldNames.add(f.getTableField().getFieldCode()+" = ?");
                fieldValues.add(record.get(f.getFieldAlias()));
            });
            StringBuffer buf = new StringBuffer();

            buf.append("update ").append(v.getTable().getTableCode())
                    .append(" set ").append(String.join(",",fieldNames))
                    .append(" where ")
                    .append(v.getKeyField().getTableField().getFieldCode())
                    .append(" = ?");
            fieldValues.add(record.get(v.getKeyField().getFieldAlias()));
            return new SqlAndParamValues(buf.toString(),fieldValues.toArray());
        }
        return null;
    }

    private static Pattern sqlParamPattern = Pattern.compile(":(\\w*)");

    public static void main(String[] args) {
        Matcher m = sqlParamPattern.matcher("select 1 from aa where a= :hello b = :foo");
        while (m.find()){
            System.out.println(m.group(1));

        }
        System.out.println(m.replaceAll("?"));

    }
    public SqlAndParamValues getQuerySql(RapMetaModelViewObject v,Map record){
        List<Object> fieldValues = new ArrayList();

        String sql = v.getObjectSql();
        //todo 支持Sql语句模板

        Matcher matcher = sqlParamPattern.matcher(sql);
        while (matcher.find()){
            fieldValues.add(record.get(matcher.group(1)));
        }

        sql = matcher.replaceAll("?");
        return new SqlAndParamValues(sql,fieldValues.toArray());
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.firePropertyChange("modelId",modelId);
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.firePropertyChange("modelName",modelName);
        this.modelName = modelName;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.firePropertyChange("modelCode",modelCode);
        this.modelCode = modelCode;
    }

    public Map<String, RapMetaModelViewObject> getModelViewObjects() {
        return modelViewObjects;
    }

    public RapMetaModelViewObject getMainViewObject() {
        return mainViewObject;
    }

    public Long getStatusFieldId() {
        return statusFieldId;
    }

    public void setStatusFieldId(Long statusFieldId) {
        this.firePropertyChange("statusFieldId",statusFieldId);
        this.statusFieldId = statusFieldId;
    }

    public RapMetaViewField getStatusField() {
        return statusField;
    }

    /*public void setModelViewObjects(Map<String, RapMetaModelViewObject> modelViewObjects) {
                this.modelViewObjects = modelViewObjects;
            }*/
    public void addModelViewObject(RapMetaModelViewObject modelViewObject){
        this.modelViewObjects.put(modelViewObject.getObjectCode(),modelViewObject);
        //主视图对象
        if(modelViewObject.getViewType()==ModelViewObjectType.MAIN) this.mainViewObject = modelViewObject;

        modelViewObject.setModel(this);
        //状态字段
        modelViewObject.getViewFields().values().stream().forEach(f->{
            if(f.getFieldId().equals(RapMetaModel.this.statusFieldId)){
                RapMetaModel.this.statusField=f;
            }
        });
    }

    public Map<String, RapMetaModelStatus> getModelStatus() {
        return modelStatus;
    }

    public void addModelStatus(RapMetaModelStatus modelStatus){
        this.modelStatus.put(modelStatus.getStatusCode(),modelStatus);
        //默认状态
        if(modelStatus.isDefaultStatus()) this.defaultMetaModelStatus = modelStatus;
    }

    /*public void setModelStatus(Map<String, RapMetaModelStatus> modelStatus) {
        this.modelStatus = modelStatus;
    }*/

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.firePropertyChange("appId",appId);
        this.appId = appId;
    }

    @Override
    public Integer getPrimaryKey() {
        return this.modelId.intValue();
    }

    @Override
    public void setPrimaryKey(Integer primaryKey) {
        this.setModelId(primaryKey.longValue());
    }
}
