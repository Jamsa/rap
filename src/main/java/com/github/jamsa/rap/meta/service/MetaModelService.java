package com.github.jamsa.rap.meta.service;

import com.github.jamsa.rap.meta.Constant;
import com.github.jamsa.rap.meta.model.*;
import com.github.pagehelper.PageInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 元模型服务，根据模型代码动态创建
 */
public class MetaModelService {

    protected MetaModelServiceListener metaModelServiceListener;
    private JdbcTemplate jdbcTemplate;

    private RapMetaModel metaModel;
        public MetaModelService(RapMetaModel metaModel,JdbcTemplate jdbcTemplate) {
        this.metaModel = metaModel;
        this.jdbcTemplate = jdbcTemplate;
    }

    public RapMetaModel getMetaModel() {
        return metaModel;
    }

    /**
     * 将数据库查询结果转换为视图对象属性
     * @param viewObject 视图对象
     * @param record     数据库查询结果
     * @return
     */
    protected Map convertQueryResult(RapMetaModelViewObject viewObject,Map record){
        viewObject.getViewFields().values().stream().forEach(vf->{
            record.put(vf.getFieldAlias(),record.get(vf.getFieldCode()));
            record.remove(vf.getFieldCode());
        });
        return record;
    }

    /**
     * 将数据库查询结果集转换为视图对象结果集
     * @param viewObject 视图对象
     * @param result 数据库查询结果集
     * @return
     */
    protected List<Map<String,Object>> convertQueryResultList(RapMetaModelViewObject viewObject,List<Map<String,Object>> result){
        Optional.ofNullable(result).map(l->{
            l.forEach(r->{
                convertQueryResult(viewObject,r);
            });
            return l;
        });
        return result;
    }

    protected Map convertRequestParams(RapMetaModelViewObject viewObject,Map params){ //<String,String>
        viewObject.getViewFields().values().stream().forEach(f->{
            String value = (String)params.get(f.getFieldAlias());
            Object result = value;
            if(value!=null) {
                try {
                    switch (f.getDataType()) {
                        case INTEGER:
                            result = Integer.parseInt(value);
                            break;
                        case FLOAT:
                            result = Float.parseFloat(value);
                            break;
                        case DOUBLE:
                            result = Double.parseDouble(value);
                            break;
                        case DATE:
                            result = new SimpleDateFormat("yyyy-MM-dd").parse(value);
                            break;
                        case TIMESTAMP:
                            result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
                            break;
                    }
                    params.put(f.getFieldAlias(), result);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return params;
    }

    protected Map convertRequestParams(String viewAlias,Map params){ //<String,String>
        metaModel.getModelViewObjects().values().stream().filter(v->viewAlias.equals(v.getViewAlias())).forEach(v->convertQueryResult(v,params));
        return params;
    }

    protected Map convertRequestParams(Map params){
        metaModel.getModelViewObjects().values().stream().forEach(v->{
            if(v.getViewType()==ModelViewObjectType.MAIN) convertQueryResult(v,params);
            else{
                Map viewParams = (Map)params.get(v.getViewAlias());
                Arrays.stream(new String[]{Constant.RECORD_ADD_ROWS_KEY,Constant.RECORD_DELETE_ROWS_KEY,Constant.RECORD_UPDATE_ROWS_KEY}).forEach(s->{
                            List<Map> records = (List<Map>)viewParams.get(s);
                            records.stream().forEach(r->convertRequestParams(v,r));
                        });
            }
        });
        return params;
    }

    /**
     * 保存视图对象的记录
     * @param viewObject 视图对象
     * @param record 行记录
     * @return
     */
    protected Map save(RapMetaModelViewObject viewObject, Map record){
        if(viewObject!=null&&viewObject.isCreatable()&&viewObject.getTableCode()!=null){
            SqlAndParamValues sqlAndParamValues = metaModel.getSaveSql(viewObject,record);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sqlAndParamValues.getSql(),PreparedStatement.RETURN_GENERATED_KEYS);
                for(int i=0;i<sqlAndParamValues.getParams().length;i++){
                    ps.setObject(i+1,sqlAndParamValues.getParams()[i]);
                }
                return ps;
            },keyHolder);

            //获取生成的主键值
            viewObject.getViewFields().values().stream().filter(f->f.getFieldType()==ModelViewFieldType.TABLE_COLUMN
                    && f.getGenerator()==ModelViewFieldGeneratorType.NATIVE
                    && keyHolder.getKeys()!=null
                    && keyHolder.getKeys().containsKey(f.getFieldCode())).forEach(f->{
                String fieldCode = f.getFieldCode();
                record.put(f.getFieldAlias(),keyHolder.getKeys().get(fieldCode));
            });
        }
        return record;
    }

    /**
     * 保存模板相关的所有视图对象的记录，包括针对各个子表的增、删、改
     * @param record 包含主表、附表及各子表的记录（addRows,deleteRows,updateRows）
     * @return
     */
    public Map saveModelRecord(Map record){
        //保存主表
        save(metaModel.getMainViewObject(),record);

        //主表主键
        Object keyValue = record.get(metaModel.getMainViewObject().getKeyField().getFieldAlias());

        //保存附表
        metaModel.getModelViewObjects().values().stream().filter(v->v.getViewType()==ModelViewObjectType.ADDITIONAL).forEach(v->{
            record.put(v.getRefField().getFieldAlias(),keyValue); //附表关联字段
            save(v,record);
        });

        //保存子表
        metaModel.getModelViewObjects().values().stream().filter(v->v.getViewType()==ModelViewObjectType.SUBTABLE).forEach(v->{
            Map viewOperations = (Map)record.get(v.getViewAlias());
            Object viewRecords = viewOperations.get(Constant.RECORD_ADD_ROWS_KEY);
            if(viewRecords!=null&&viewRecords instanceof List) {
                ((List) viewRecords).stream().forEach(row->{
                    Map rowRecord = (Map)row;
                    rowRecord.put(v.getRefField().getFieldAlias(),keyValue); //子表关联字段
                    save(v, rowRecord);
                });
            }
        });

        return record;
    }

    /**
     * 根据主键查询主表记录
     * @param id 主表记录id
     * @return
     */
    public Map findModelRecordByKey(Object id){
        return Optional.ofNullable(metaModel.getMainViewObject()).map(RapMetaViewObject::getKeyField)
                .map(f->{
                    Map<String,Object> params = new HashMap();
                    params.put(f.getFieldAlias(),id);
                    SqlAndParamValues sqlAndParamValues = metaModel.getQuerySql(metaModel.getMainViewObject(),params);
                    Map result= jdbcTemplate.queryForMap(sqlAndParamValues.getSql(),sqlAndParamValues.getParams());
                    return convertQueryResult(metaModel.getMainViewObject(),result);
                }).orElse(null);
    }

    /**
     * 更新视图对象记录
     * @param viewObject 视图对象
     * @param record 视图对象行
     * @return
     */
    protected Map update(RapMetaModelViewObject viewObject, Map record){
        SqlAndParamValues sqlAndParamValues = metaModel.getUpdateSql(viewObject,record);
        jdbcTemplate.update(sqlAndParamValues.getSql(),sqlAndParamValues.getParams());
        return record;
    }

    /**
     * 更新模板相关各视图对象记录
     * @param record  包含主表、附表及各子表的记录（addRows,deleteRows,updateRows）
     * @return
     */
    public Map updateModelRecord(Map record){
        //主表主键
        Object keyValue = record.get(metaModel.getMainViewObject().getKeyField().getFieldAlias());

        update(metaModel.getMainViewObject(),record);

        metaModel.getModelViewObjects().values().stream().filter(v->v.getViewType()==ModelViewObjectType.ADDITIONAL).forEach(v->{
            update(v,record);
        });

        //更新子表
        metaModel.getModelViewObjects().values().stream().filter(v->v.getViewType()==ModelViewObjectType.SUBTABLE).forEach(v->{
            Map viewOperations = (Map)record.get(v.getViewAlias());
            Object viewRecords = viewOperations.get(Constant.RECORD_ADD_ROWS_KEY);
            if(viewRecords!=null&&viewRecords instanceof List) {
                ((List) viewRecords).stream().forEach(row->{
                    Map rowRecord = (Map)row;
                    rowRecord.put(v.getRefField().getFieldAlias(),keyValue); //子表关联字段
                    save(v, rowRecord);
                });
            }

            viewRecords = viewOperations.get(Constant.RECORD_UPDATE_ROWS_KEY);
            if(viewRecords!=null&&viewRecords instanceof List) {
                ((List) viewRecords).stream().forEach(row->{
                    Map rowRecord = (Map)row;
                    update(v, rowRecord);
                });
            }

            viewRecords = viewOperations.get(Constant.RECORD_DELETE_ROWS_KEY);
            if(viewRecords!=null&&viewRecords instanceof List) {
                ((List) viewRecords).stream().forEach(row->{
                    Map rowRecord = (Map)row;
                    delete(v, rowRecord);
                });
            }

        });

        return record;
    }

    /**
     * 删除-视图对象中的行
     * @param viewObject 视图对象
     * @param record 视图对象行
     * @return
     */
    protected int delete(RapMetaModelViewObject viewObject, Object record){
        Object id = (record instanceof Map)?((Map)record).get(viewObject.getKeyField().getFieldAlias()):record;
        return Optional.ofNullable(id)
                .map(key -> metaModel.getDeleteSql(viewObject,key))
                .map(sp->jdbcTemplate.update(sp.getSql(),sp.getParams())).orElse(0);
    }

    /**
     * 删除-视图对象中的行
     * @param viewAlias 视图对象别名
     * @param record 视图对象行
     * @return
     */
    public int delete(String viewAlias, Object record){
        return metaModel.getModelViewObjects().values().stream()
                .filter(v->viewAlias.equals(v.getViewAlias()))
                .map(v-> delete(v,record))
                .findFirst().orElse(0);
    }

    /**
     * 删除模型各视图对象中的行
     * @param id 主表主键
     * @return
     */
    public int deleteModelRecordByKey(Object id){
        return metaModel.getDeleteByMainKeySqls(id).stream().mapToInt(sp -> jdbcTemplate.update(sp.getSql(),sp.getParams())).sum();
    }

    /**
     * 删除模型各视图对象中的行
     * @param record 模型行对象
     * @return
     */
    public int deleteModelRecord(Map record){
        return Optional.ofNullable(metaModel.getMainViewObject())
                .map(RapMetaViewObject::getKeyField)
                .map(RapMetaViewField::getFieldAlias)
                .map(key -> record.get(key)).map(this::deleteModelRecordByKey).orElse(0);

    }

    /**
     * 查询模型主视图对象
     * @param record 查询条件
     * @return
     */
    public List<Map<String,Object>> findModelRecordByCondition(Map record) {
        RapMetaModelViewObject v = metaModel.getMainViewObject();
        return findByCondition(v,record);
    }

    /**
     * 查询视图对象
     * @param v 视图对象
     * @param record 查询条件
     * @return
     */
    protected List<Map<String,Object>> findByCondition(RapMetaModelViewObject v, Map record){
        SqlAndParamValues sqlAndParamValues = metaModel.getQuerySql(v,record);

        List result = jdbcTemplate.queryForList(sqlAndParamValues.getSql(),sqlAndParamValues.getParams());
        return convertQueryResultList(v,result);
    }

    /**
     * 查询视图对象
     * @param viewAlias 模型视图对象别名
     * @param record 查询条件
     * @return
     */
    public List<Map<String,Object>> findByCondition(String viewAlias, Map record){
        return metaModel.getModelViewObjects().values().stream()
                .filter(v->viewAlias.equals(v.getViewAlias()))
                .map(v-> findByCondition(v,record))
                .findFirst().orElse(null);
    }

    /**
     * 分页查询模型主视图对象
     * @param record 查询条件
     * @param page 分页条件
     * @return
     */
    public PageInfo findModelRecordByPage(Map record, PageInfo page){
        RapMetaModelViewObject v = metaModel.getMainViewObject();
        return findByPage(v,record,page);
    }

    /**
     * 分页查询视图对象
     * @param v 视图对象
     * @param record 查询条件
     * @param page 分页条件
     * @return
     */
    protected PageInfo findByPage(RapMetaModelViewObject v, Map record, PageInfo page){

        SqlAndParamValues sqlAndParamValues = metaModel.getQuerySql(v,record);
        String sql = sqlAndParamValues.getSql();
        String countSql = "select count(1) as CT from ("+ sql +") as AA";

        int offset = (page.getPageNum()<2)?0:(page.getPageNum()-1)*page.getPageSize();
        sql =  sql + " limit " + offset + "," + page.getPageSize();

        Long total = jdbcTemplate.queryForObject(countSql,Long.class,sqlAndParamValues.getParams());

        List<Map<String,Object>> data = jdbcTemplate.queryForList(sqlAndParamValues.getSql(),sqlAndParamValues.getParams());
        data = convertQueryResultList(v,data);
        page.setList(data);
        page.setTotal(total);
        return page;
    }

    /**
     * 分页查询视图对象
     * @param viewAlias 模型视图对象别名
     * @param record 查询条件
     * @param page 分页条件
     * @return
     */
    public PageInfo findByPage(String viewAlias, Map record, PageInfo page){
        return metaModel.getModelViewObjects().values().stream()
                .filter(v->viewAlias.equals(v.getViewAlias()))
                .map(v-> findByPage(v,record,page))
                .findFirst().orElse(null);
    }
}
