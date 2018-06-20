package com.github.jamsa.rap.core.model;

import org.springframework.beans.BeanUtils;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 实例类的Setter增强器，用于处理表字段的部分更新
 */
public class SetterInterceptor implements MethodInterceptor {
    private static final String SETTER_PREFIX="set";

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        if(method.getName().startsWith(SETTER_PREFIX)){
            if(o instanceof BaseEntity && objects.length>0){
                BaseEntity baseEntity = (BaseEntity)o;
                String propertyName = BeanUtils.findPropertyForMethod(method).getName();
                baseEntity.firePropertyChange(propertyName,objects[0]);
            }
        }
        return methodProxy.invoke(o,objects);
    }
}
