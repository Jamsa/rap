package com.github.jamsa.rap.meta.service;

import com.github.jamsa.rap.meta.Constant;
import com.github.jamsa.rap.meta.model.*;
import com.github.pagehelper.PageInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.swing.text.html.Option;
import java.sql.PreparedStatement;
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

    //删除视图对象中的行
    protected int deleteViewRecord(RapMetaModelViewObject viewObject,Object record){
        Object id = (record instanceof Map)?((Map)record).get(viewObject.getKeyField().getFieldAlias()):record;
        return Optional.ofNullable(id)
                .map(key -> metaModel.getDeleteSql(viewObject,key))
                .map(sp->jdbcTemplate.update(sp.getSql(),sp.getParams())).orElse(0);
    }

    //删除模型记录及其子记录，参数为模型记录主键
    public int deleteByPrimaryKey(Object id){
        return metaModel.getDeleteByMainKeySqls(id).stream().mapToInt(sp -> jdbcTemplate.update(sp.getSql(),sp.getParams())).sum();
    }

    protected Map saveViewRecord(RapMetaModelViewObject viewObject, Map record){
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

    public Map save(Map record){
        //保存主表
        saveViewRecord(metaModel.getMainViewObject(),record);

        //主表主键
        Object keyValue = record.get(metaModel.getMainViewObject().getKeyField().getFieldAlias());

        //保存附表
        metaModel.getModelViewObjects().values().stream().filter(v->v.getViewType()==ModelViewObjectType.ADDITIONAL).forEach(v->{
            record.put(v.getRefField().getFieldAlias(),keyValue); //附表关联字段
            saveViewRecord(v,record);
        });

        //保存子表
        metaModel.getModelViewObjects().values().stream().filter(v->v.getViewType()==ModelViewObjectType.SUBTABLE).forEach(v->{
            Map viewOperations = (Map)record.get(v.getViewAlias());
            Object viewRecords = viewOperations.get(Constant.RECORD_ADD_ROWS_KEY);
            if(viewRecords!=null&&viewRecords instanceof List) {
                ((List) viewRecords).stream().forEach(row->{
                    Map rowRecord = (Map)row;
                    rowRecord.put(v.getRefField().getFieldAlias(),keyValue); //子表关联字段
                    saveViewRecord(v, rowRecord);
                });
            }
        });

        return record;
    }

    protected Map convertQueryResult(RapMetaModelViewObject viewObject,Map record){
        viewObject.getViewFields().values().stream().forEach(vf->{
            record.put(vf.getFieldAlias(),record.get(vf.getFieldCode()));
            record.remove(vf.getFieldCode());
        });
        return record;
    }

    protected List<Map<String,Object>> convertQueryResultList(RapMetaModelViewObject viewObject,List<Map<String,Object>> result){
        Optional.ofNullable(result).map(l->{
            l.forEach(r->{
                convertQueryResult(viewObject,r);
            });
            return l;
        });
        return result;
    }

    public Map findByPrimaryKey(Object id){
        return Optional.ofNullable(metaModel.getMainViewObject()).map(RapMetaViewObject::getKeyField)
                .map(f->{
                    Map<String,Object> params = new HashMap();
                    params.put(f.getFieldAlias(),id);
                    SqlAndParamValues sqlAndParamValues = metaModel.getQuerySql(metaModel.getMainViewObject(),params);
                    Map result= jdbcTemplate.queryForMap(sqlAndParamValues.getSql(),sqlAndParamValues.getParams());
                    return convertQueryResult(metaModel.getMainViewObject(),result);
                }).orElse(null);
    }

    protected Map updateViewRecord(RapMetaModelViewObject viewObject, Map record){
        SqlAndParamValues sqlAndParamValues = metaModel.getUpdateSql(viewObject,record);
        jdbcTemplate.update(sqlAndParamValues.getSql(),sqlAndParamValues.getParams());
        return record;
    }


    public Map update(Map record){
        //主表主键
        Object keyValue = record.get(metaModel.getMainViewObject().getKeyField().getFieldAlias());

        updateViewRecord(metaModel.getMainViewObject(),record);

        metaModel.getModelViewObjects().values().stream().filter(v->v.getViewType()==ModelViewObjectType.ADDITIONAL).forEach(v->{
            updateViewRecord(v,record);
        });

        //更新子表
        metaModel.getModelViewObjects().values().stream().filter(v->v.getViewType()==ModelViewObjectType.SUBTABLE).forEach(v->{
            Map viewOperations = (Map)record.get(v.getViewAlias());
            Object viewRecords = viewOperations.get(Constant.RECORD_ADD_ROWS_KEY);
            if(viewRecords!=null&&viewRecords instanceof List) {
                ((List) viewRecords).stream().forEach(row->{
                    Map rowRecord = (Map)row;
                    rowRecord.put(v.getRefField().getFieldAlias(),keyValue); //子表关联字段
                    saveViewRecord(v, rowRecord);
                });
            }

            viewRecords = viewOperations.get(Constant.RECORD_UPDATE_ROWS_KEY);
            if(viewRecords!=null&&viewRecords instanceof List) {
                ((List) viewRecords).stream().forEach(row->{
                    Map rowRecord = (Map)row;
                    updateViewRecord(v, rowRecord);
                });
            }

            viewRecords = viewOperations.get(Constant.RECORD_DELETE_ROWS_KEY);
            if(viewRecords!=null&&viewRecords instanceof List) {
                ((List) viewRecords).stream().forEach(row->{
                    Map rowRecord = (Map)row;
                    deleteViewRecord(v, rowRecord);
                });
            }

        });

        return record;
    }

    //删除模型记录及其子记录，参数为模型记录
    public int delete(Map record){
        return Optional.ofNullable(metaModel.getMainViewObject())
                .map(RapMetaViewObject::getKeyField)
                .map(RapMetaViewField::getFieldAlias)
                .map(key -> record.get(key)).map(this::deleteByPrimaryKey).orElse(0);

    }

    public List<Map<String,Object>> findByCondition(Map record){
        RapMetaModelViewObject v = metaModel.getMainViewObject();

        SqlAndParamValues sqlAndParamValues = metaModel.getQuerySql(v,record);

        List result = jdbcTemplate.queryForList(sqlAndParamValues.getSql(),sqlAndParamValues.getParams());
        return convertQueryResultList(v,result);
    }

    public PageInfo findByPage(Map record, PageInfo page){
        RapMetaModelViewObject v = metaModel.getMainViewObject();

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
}
