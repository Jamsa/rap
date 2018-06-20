package com.github.jamsa.rap.core.model;


import org.springframework.cglib.proxy.Enhancer;

/**
 * 实体工厂类
 * 由工厂生成的实体类会添加 setterInterceptor 以便进行部分更新
 */
public class EntityFactory {

    private static final SetterInterceptor setterInterceptor = new SetterInterceptor();

    public static final <T extends BaseEntity> T newInstance(Class<T> clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(setterInterceptor);
        return (T)enhancer.create();
    }
}
