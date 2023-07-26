package ${basePackage}.${modelName}.facade.impl;

import cn.com.flaginfo.framework.rsql.mongodb.dto.Rsql;
import cn.com.flaginfo.framework.rsql.mongodb.dto.RsqlBase;
import cn.com.flaginfo.framework.vo.ResultVO;
import cn.com.flaginfo.framework.webmvc.utils.ResultGeneratorUtil;

import ${basePackage}.${modelName}.facade.I${model}Facade;
import ${basePackage}.${modelName}.model.${model};
import ${basePackage}.${modelName}.service.I${model}Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelName}.facade.impl
 * @Description: code by codeGenerator
 * @date ${.now}
 */
@Slf4j
@Component
public class ${model}FacadeImpl implements I${model}Facade {
    @Autowired
    private I${model}Service service;
    @Override
    public ResultVO save(${model} model) {
        String id = service.add(model);
        Map map = new HashMap(1);
        map.put("id",id);
        return ResultGeneratorUtil.success(map);
    }

    @Override
    public ResultVO update(${model} model) {
        service.update(model);
        return ResultGeneratorUtil.success("修改成功");
    }


    @Override
    public ResultVO get(String id) {
        return ResultGeneratorUtil.success(service.getById(id));
    }

    @Override
    public ResultVO queryByPage(RsqlBase base, Pageable page) {
        Rsql rsql = Rsql.builder().where(base.getWhere()).sort(base.getSort()).page(page.getPageNumber()).pageSize(page.getPageSize()).build();
        ResultVO<List<${model}>> modelResult = ParseUtils
                .MongoPageToR(service.findByPage(rsql),page);
        return modelResult;
    }

    @Override
    public ResultVO del(String id) {
        service.del(id);
        return ResultGeneratorUtil.success("移除成功");
    }

}
