package com.github.jamsa.rap.meta.controller;

import com.github.jamsa.rap.meta.service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 元数据模块主控制器
 */
@RestController
public class MetaController {
    /*@Autowired
    private MetaDataService metaDataService;*/

    private Map<String,MetaModelController> metaModelControllers = new ConcurrentHashMap<>();

    @Autowired
    protected MetaService metaService;

    @RequestMapping("/meta-api/refresh/{modelCode}")
    public Object refresh(@PathVariable("modelCode") String modelCode, HttpServletRequest request) throws IOException {
        metaModelControllers.remove(modelCode);
        metaService.removeMetaModelService(modelCode);
        return null;
    }

    @RequestMapping("/meta-api/{modelCode}/**")
    public Object dispatch(@PathVariable("modelCode") String modelCode, HttpServletRequest request) throws IOException {
        MetaModelController controller = metaModelControllers.get(modelCode);
        if(controller==null){
            controller = new MetaModelController(modelCode, metaService.getMetaModelService(modelCode));
            metaModelControllers.put(modelCode,controller);
        }
        return controller.dispatch(request);
    }

}
