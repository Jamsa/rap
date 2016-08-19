package com.github.jamsa.rap.core.orm;


import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by zhujie on 16/8/15.
 */
public class BaseEntityDao<T,P extends Serializable> extends BaseDao {
    private Class<T> entityClass;

    public BaseEntityDao() {
        entityClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T get(P id){
        return this.getHibernateTemplate().get(entityClass,id);
    }

    public T create(){
        try {
            Constructor<T> constructor = entityClass.getConstructor(new Class[] {});
            return constructor.newInstance(new Object[]{});
        }catch (Exception e){
            logger.error("新建实例对象时出错!",e);
            return null;
        }
    }

    public List<T> loadAll(){
        this.loadAll(this.entityClass);
    }

    public void delete(P id){
        this.delete(this.get(id));
    }

}
