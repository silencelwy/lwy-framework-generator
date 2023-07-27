package ${basePackage}.${modelName}.facade;


import cn.com.flaginfo.vo.UserInfo;
import cn.com.flaginfo.framework.rsql.mongodb.dto.RsqlBase;
import cn.com.flaginfo.framework.vo.ResultVO;
import ${basePackage}.${modelName}.model.${model};
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelName}.facade
 * @Description: code by codeGenerator
 * @date ${.now}
 */
public interface I${model}Facade {
    ResultVO save(${model} model);
    ResultVO update(${model} model);
    ResultVO get(String id);
    ResultVO<List<${model}>>  queryByPage(RsqlBase base, Pageable page);
    ResultVO del(String id);
    void export(RsqlBase queryRsql, UserInfo userInfo) throws Exception;
}
