package com.github.jamsa.rap.core.controller;

import com.github.jamsa.rap.core.model.BaseEntity;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 处理BaseEntity通过EntityFactory来构造
 */
public class EntityResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(BaseEntity.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //EntityFactory.newInstance(parameter.getParameterType())
        //MappingJackson2HttpMessageConverter
        //MappingJackson2HttpMessageConverter
        return null;
    }
}
