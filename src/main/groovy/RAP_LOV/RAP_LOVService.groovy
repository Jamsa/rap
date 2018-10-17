import com.github.jamsa.rap.meta.model.RapMetaModel
import com.github.jamsa.rap.meta.service.MetaModelService
import org.springframework.jdbc.core.JdbcTemplate

/**
 * 测试热加载服务类的脚本
 */
class RAP_LOVService extends MetaModelService{
    RAP_LOVService(RapMetaModel metaModel, JdbcTemplate jdbcTemplate) {
        super(metaModel, jdbcTemplate)
    }

    @Override
    int delete(String viewAlias, Object record) {
        return tx.execute(int.class,{->super.delete(viewAlias, record)});
    }

    @Override
    Map updateModelRecord(Map record) {
        println("..............")
        return tx.execute(Map.class,{->super.updateModelRecord(record)});
    }
}