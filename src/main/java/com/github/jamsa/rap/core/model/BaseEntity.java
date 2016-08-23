package com.github.jamsa.rap.core.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhujie on 16/8/16.
 */
public abstract class BaseEntity<P extends Serializable> implements Serializable {
    private Map changedProperties = new HashMap();

    public Map getChangedProperties() {
        return changedProperties;
    }

    public boolean isPropertyChanged(String propertyName){
        return this.changedProperties.containsKey(propertyName);
    }

    protected void firePropertyChange(String propertyName,Object value){
        this.changedProperties.put(propertyName,value);
    }

    public abstract P getPrimaryKey();
    public abstract void setPrimaryKey(P primaryKey);

}
