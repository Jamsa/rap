import com.github.jamsa.rap.meta.controller.MetaModelController
import com.github.jamsa.rap.meta.service.MetaModelService
import org.springframework.http.ResponseEntity

import javax.servlet.http.HttpServletRequest

/**
 * 测试热加载控制器的脚本
 */
class RAP_LOVController extends MetaModelController{
    RAP_LOVController(String modelCode, MetaModelService metaModelService) {
        super(modelCode, metaModelService)
        println "hello groovy dddssdddddddddd11111ssss"
    }

    @Override
    ResponseEntity findByPage(HttpServletRequest request) {
        println "findByPag ddsdfsde"
        return super.findByPage(request)
    }
}
