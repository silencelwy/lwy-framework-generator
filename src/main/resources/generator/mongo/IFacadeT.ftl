package ${basePackage}.${modelName}.facade;

import cn.com.flaginfo.framework.rsql.mongodb.dto.RsqlBase;
import cn.com.flaginfo.framework.vo.ResultVO;
import ${basePackage}.${modelName}.model.${model};
import org.springframework.data.domain.Pageable;

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
    ResultVO queryByPage(RsqlBase base, Pageable page);
    ResultVO del(String id);
}
