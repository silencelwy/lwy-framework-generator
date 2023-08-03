package ${basePackage}.${modelPackage}.service;

import cn.com.flaginfo.framework.rsql.mongodb.dto.Rsql;
import ${basePackage}.${modelPackage}.model.${model};
import org.springframework.data.domain.Page;

/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelPackage}.service
 * @Description: code by codeGenerator
 * @date ${.now}
 */
public interface I${model}Service {
    /**
     * 明细
     * @param id
     * @return
     */
    ${model} getById(String id);

    /**
     * 新增
     * @param model
     * @return
     */
    String add(${model} model);

    /**
     * 更新
     * @param model
     */
    void update(${model} model);

    /**
     * 分页查询
     * @param rsql
     * @return
     */
    Page<${model}> findByPage(Rsql rsql);

    /**
     * 删除
     *
     * @param id
     */
    void del(String id);
}