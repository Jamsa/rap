package com.github.jamsa.rap.core.orm;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhujie on 16/8/15.
 */
public class BaseDao extends HibernateDaoSupport {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional
    public <T> T save(T entity){
        this.getHibernateTemplate().save(entity);
        return entity;
    }

    @Transactional
    public <T> T update(T entity){
        this.getHibernateTemplate().update(entity);
        return entity;
    }

    @Transactional
    public <T> T saveOrUpdate(T entity){
        this.getHibernateTemplate().saveOrUpdate(entity);
        return entity;
    }

    @Transactional
    public <T> T merge(T entity){
        return this.getHibernateTemplate().merge(entity);
    }

    @Transactional
    public <T> void delete(T entity){
        this.getHibernateTemplate().delete(entity);
    }

    @Transactional
    public void deleteAll(Collection<?> entities){
        this.getHibernateTemplate().deleteAll(entities);
    }

    public <T> T get(Class<T> entityClass, Serializable id){
        return this.getHibernateTemplate().get(entityClass,id);
    }

    public <T> List<T> loadAll(Class<T> entityClass){
        return this.getHibernateTemplate().loadAll(entityClass);
    }

    public <T> List<T> findByExample(T entity){
        return this.getHibernateTemplate().findByExample(entity);
    }

    public <T> List<T> findByExample(T entity,int firstResult,int maxResuylts){
        return this.getHibernateTemplate().findByExample(entity,firstResult,maxResuylts);
    }

    public <T> List<T> queryForList(final String hql,final Map<String,Object> params,final boolean cacheable,final int firstResult,final int maxResult){
        if(StringUtils.isEmpty(hql)){
            throw new DataRetrievalFailureException("hql语句为空!");
        }
        return this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<T>>() {
            public List<T> doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                query.setCacheable(cacheable);

                for(Map.Entry<String,Object> entry:params.entrySet()){
                    if(entry.getValue()!=null){
                        query.setParameter(entry.getKey(),entry.getValue());
                    }
                }

                if(firstResult>0)
                    query.setFirstResult(firstResult);
                if(maxResult>0)
                    query.setMaxResults(maxResult);
                return (List<T>)query.list();
            }
        });
    }

    public <T> List<T> queryForList(final String hql,final Object params,final boolean cacheable,final int firstResult,final int maxResult){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        BeanUtils.copyProperties(params,paramMap);
        return this.queryForList(hql,paramMap,cacheable,firstResult,maxResult);
    }

    public <T> T queryForObject(final String hql,final Map params,final boolean cachable){
        List<T> result = this.queryForList(hql,params,cachable,0,1);
        return CollectionUtils.isEmpty(result)?null:result.get(0);
    }

    public <T> T queryForObject(final String hql,final Object params,final boolean cachable){
        Map<String,Object> paramMap = new HashMap<String,Object>();
        BeanUtils.copyProperties(params,paramMap);
        return this.queryForObject(hql,paramMap,cachable);
    }

    public void test(){
        this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>() {
            public Object doInHibernate(Session session) throws HibernateException {

                return null;
            }
        });
    }

}
