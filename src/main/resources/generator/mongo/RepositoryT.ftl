
package ${basePackage}.${modelName}.service;
import cn.com.flaginfo.framework.rsql.crud.MongodbRsqlCrudRepository;
import ${basePackage}.${modelName}.model.${model};

/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelName}.service
 * @Description: code by codeGenerator
 * @date ${.now}
 */
public interface I${model}Repository extends MongodbRsqlCrudRepository<${model},String> {
}
