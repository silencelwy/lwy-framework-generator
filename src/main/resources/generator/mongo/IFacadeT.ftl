package ${basePackage}.${modelPackage}.facade;


import cn.com.flaginfo.vo.UserInfo;
import cn.com.flaginfo.framework.rsql.mongodb.dto.RsqlBase;
import cn.com.flaginfo.framework.vo.ResultVO;
import ${basePackage}.${modelPackage}.model.${model};
import ${basePackage}.${modelPackage}.vo.${model}Vo;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface I${model}Facade {
    ResultVO save(${model}Vo modelVo);
    ResultVO update(${model}Vo modelVo);
    ResultVO get(String id);
    ResultVO<List<${model}>>  queryByPage(RsqlBase base, Pageable page);
    ResultVO del(String id);
    void export(RsqlBase queryRsql, UserInfo userInfo) throws Exception;
}
