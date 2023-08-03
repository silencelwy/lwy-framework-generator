package ${basePackage}.${modelPackage}.service.impl;

import cn.com.flaginfo.framework.id.IdGenerator;
import cn.com.flaginfo.framework.rsql.mongodb.dto.Rsql;

import ${basePackage}.${modelPackage}.model.${model};
import ${basePackage}.${modelPackage}.service.I${model}Repository;
import ${basePackage}.${modelPackage}.service.I${model}Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelPackage}.service.impl
 * @Description: code by codeGenerator
 * @date ${.now}
 */
@Service
@Slf4j
public class ${model}ServiceImpl implements I${model}Service {
    @Autowired
    private I${model}Repository repository;

    @Override
    public ${model} getById(String id) {
        Optional<${model}> optional = repository.findById(id);
        if (optional != null
                && optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public String add(${model} model) {
        model.setId(IdGenerator.getInstance().generateIdToString());
        model.setCtime(new Date());
        model.setDel(false);
        repository.insert(model);
        return model.getId();
    }

    @Override
    public void update(${model} model) {
        model.setUtime(new Date());
        repository.update(model);
    }

    @Override
    public Page<${model}> findByPage(Rsql rsql) {
        return repository.findAll(rsql);
    }

    @Override
    public void del(String id) {
        repository.deleteById(id);
    }
}
