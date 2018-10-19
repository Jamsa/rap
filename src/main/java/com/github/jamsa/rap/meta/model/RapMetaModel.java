package com.github.jamsa.rap.meta.model;

import com.github.jamsa.rap.core.model.BaseEntity;
import com.github.jamsa.rap.meta.util.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RapMetaModel  extends BaseEntity<Integer> {

    private static final Logger logger = LoggerFactory.getLogger(RapMetaModel.class);

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

    private TemplateUtil sqlTemplateUtil = new TemplateUtil();

    public RapMetaModel() {
    }

    public boolean isSubTableViewAlias(String name){
        return this.getModelViewObjects().values().stream()
                .filter(v->v.getViewType()==ModelViewObjectType.SUBTABLE && name.equals(v.getViewAlias()))
                .findFirst().orElse(null)!=null;
    }


    protected String getDeleteByMainKeySql(RapMetaModelViewObject v){
        return Optional.ofNullable(v).filter(RapMetaModelViewObject::isDeletable)
                .map(mv->mv.getViewType()==ModelViewObjectType.MAIN?mv.getKeyField():mv.getRelField())
                //.map(RapMetaViewField::getTableField)
                .map(tf->"delete from "+tf.getViewObject().getTableCode()+" where "+tf.getFieldCode()+"=?").orElse(null);
    }

    //private String[] deleteByPrimaryKeySqls=null;
    public List<SqlAndParamValues> getDeleteByMainKeySqls(Object id){
        return getModelViewObjects().values().stream().sorted(Comparator.comparing(RapMetaModelViewObject::getViewType).reversed())
                .map(v->getDeleteByMainKeySql(v))
                .filter(s->!StringUtils.isEmpty(s))
                .map(s->new SqlAndParamValues(s,new Object[]{id})).collect(Collectors.toList());

    }

    public SqlAndParamValues getDeleteSql(RapMetaModelViewObject v, Object id){
        return Optional.ofNullable(v).filter(vo->!StringUtils.isEmpty(vo.getTableCode()) && vo.isDeletable())
                .map(RapMetaViewObject::getKeyField)
                .map(tf->new SqlAndParamValues("delete from "+tf.getViewObject().getTableCode()+" where "+tf.getFieldCode()+"=?",new Object[]{id}))
                .orElse(null);
    }

    public SqlAndParamValues getSaveSql(RapMetaModelViewObject v,Map record){
        return Optional.ofNullable(v).filter(vo->vo.getTableCode()!=null&&vo.isCreatable())
                .map(vo->{
                    List<String> fieldNames = new ArrayList();
                    List<String> fieldParams = new ArrayList();
                    List<Object> fieldValues = new ArrayList();
                    vo.getViewFields().values().stream()
                            .filter(f->f.getFieldType()==ModelViewFieldType.TABLE_COLUMN && f.getGenerator()!=ModelViewFieldGeneratorType.NATIVE) // 数据库列，且非数据库生成的字段
                            .forEach(f->{
                                fieldNames.add(f.getFieldCode());
                                fieldParams.add("?");
                                switch (f.getGenerator()){
                                    case UUID: fieldValues.add(UUID.randomUUID());break;
                                    default: fieldValues.add(record.get(f.getFieldAlias()));
                                }

                            });
                    StringBuffer buf = new StringBuffer();
                    buf.append("insert into ").append(vo.getTableCode())
                            .append("(").append(String.join(",",fieldNames))
                            .append(") values(")
                            .append(String.join(",",fieldParams))
                            .append(")");
                    return new SqlAndParamValues(buf.toString(),fieldValues.toArray());
                }).orElse(null);

    }

    public SqlAndParamValues getUpdateSql(RapMetaModelViewObject v,Map record){
        return Optional.ofNullable(v).filter(vo->vo.getTableCode()!=null && vo.isUpdatable())
                .map(vo->{
                    List<String> fieldNames = new ArrayList();
                    List<Object> fieldValues = new ArrayList();

                    vo.getViewFields().values().stream()
                            .filter(f->f.getFieldType()==ModelViewFieldType.TABLE_COLUMN && !f.isKeyField() && f.getGenerator()==ModelViewFieldGeneratorType.INPUT)
                            .forEach(f->{
                                fieldNames.add(f.getFieldCode()+" = ?");
                                fieldValues.add(record.get(f.getFieldAlias()));
                            });
                    StringBuffer buf = new StringBuffer();

                    buf.append("update ").append(v.getTableCode())
                            .append(" set ").append(String.join(",",fieldNames))
                            .append(" where ")
                            .append(v.getKeyField().getFieldCode())
                            .append(" = ?");
                    fieldValues.add(record.get(v.getKeyField().getFieldAlias()));
                    return new SqlAndParamValues(buf.toString(),fieldValues.toArray());
                }).orElse(null);

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

        //String sql = v.getObjectSql();
        String sql = sqlTemplateUtil.process(v,record);

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

        //创建SQL模板
        sqlTemplateUtil.putTemplate(modelViewObject.getObjectCode(), modelViewObject.getObjectSql());
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
