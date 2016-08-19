package com.github.jamsa.rap.core.model;

import java.io.Serializable;

/**
 * Created by zhujie on 16/8/16.
 */
public interface BaseEntity<P extends Serializable> {
    public P getPrimaryKey();
    public void setPrimaryKey(P primaryKey);

}
