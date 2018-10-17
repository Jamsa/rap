package com.github.jamsa.rap.meta.util;

import com.github.jamsa.rap.meta.model.RapMetaModelViewObject;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.Map;

public class TemplateUtil {
    private static final Logger logger = LoggerFactory.getLogger(TemplateUtil.class);

    private Configuration configuration;
    private StringTemplateLoader sqlTemplateLoader;

    public TemplateUtil() {
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        sqlTemplateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(sqlTemplateLoader);
    }

    public String process(RapMetaModelViewObject v, Map record) throws RuntimeException{
        StringWriter writer = new StringWriter();
        try {
            configuration.getTemplate(v.getObjectCode()).process(record,writer);
        } catch (Exception e) {
            logger.error("SQL模板解析出错",e);
            throw new RuntimeException("SQL模板解析出错",e);
        }
        return writer.toString();
    }

    public void putTemplate(String name,String sqlTemplate){
        sqlTemplateLoader.putTemplate(name, sqlTemplate);
    }
}
