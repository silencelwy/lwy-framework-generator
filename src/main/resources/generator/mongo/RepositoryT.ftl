
package ${basePackage}.${modelPackage}.service;
import cn.com.flaginfo.framework.rsql.crud.MongodbRsqlCrudRepository;
import ${basePackage}.${modelPackage}.model.${model};


public interface I${model}Repository extends MongodbRsqlCrudRepository<${model},String> {
}
