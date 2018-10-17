package com.github.jamsa.rap.meta.controller;

import com.github.jamsa.rap.meta.model.RapMetaModel;
import com.github.jamsa.rap.meta.service.MetaModelService;
import com.github.jamsa.rap.meta.service.MetaService;
import groovy.lang.GroovyClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 元数据模块主控制器
 */
@RestController
public class MetaController {
    /*@Autowired
    private MetaDataService metaDataService;*/

    @Value("${rap.meta.scriptDir}")
    private String scriptDir;

    private Map<String,MetaModelController> metaModelControllers = new ConcurrentHashMap<>();

    private Map<String,ClassLoader> classLoaderMap = new WeakHashMap();

    @Autowired
    protected MetaService metaService;

    @RequestMapping("/meta-api/refresh/{modelCode}")
    public Object refresh(@PathVariable("modelCode") String modelCode, HttpServletRequest request) throws IOException {
        metaModelControllers.remove(modelCode);
        metaService.removeMetaModelService(modelCode);
        //System.gc();
        //System.out.println("######"+classLoaderMap.size());
        return null;
    }

    @RequestMapping("/meta-api/{modelCode}/**")
    public Object dispatch(@PathVariable("modelCode") String modelCode, HttpServletRequest request) throws IOException {
        MetaModelController controller = metaModelControllers.get(modelCode);
        if(controller==null){
            GroovyClassLoader groovyClassLoader = null;
            Class controllerClass = MetaModelController.class;
            Class serviceClass = MetaModelService.class;
            File controllerScript = new File(scriptDir+"/"+modelCode+"/"+modelCode+"Controller.groovy");
            File serviceScript = new File(scriptDir+"/"+modelCode+"/"+modelCode+"Service.groovy");
            if(controllerScript.exists()||serviceScript.exists()){
                groovyClassLoader = new GroovyClassLoader();
                //classLoaderMap.put(modelCode+System.currentTimeMillis(),groovyClassLoader);
                if(controllerScript.exists()) controllerClass = groovyClassLoader.parseClass(controllerScript);
                if(serviceScript.exists()) serviceClass = groovyClassLoader.parseClass(serviceScript);
            }
            MetaModelService service = metaService.getMetaModelService(modelCode,serviceClass);

            try {
                Constructor<MetaModelController> constructor = controllerClass.getConstructor(String.class, MetaModelService.class);
                controller = constructor.newInstance(modelCode, service);
            }catch (Exception e){
                e.printStackTrace();

                //如果不是MetaModelController则加载默认的
                if(!controllerClass.equals(MetaModelController.class)) {
                    controller = new MetaModelController(modelCode, metaService.getMetaModelService(modelCode));
                }
            }

            metaModelControllers.put(modelCode,controller);
        }
        return controller.dispatch(request);
    }

}
