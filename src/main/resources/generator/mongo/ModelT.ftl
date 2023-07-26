package ${basePackage}.${modelName}.model;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import cn.com.flaginfo.framework.model.BaseModel;

/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelName}.model
 * @Description: code by codeGenerator
 * @date ${.now}
 */
@Data
@Document(collection="${collectionName}")
public class ${model} extends BaseModel {
<#if columns?? && (columns?size > 0) >
<#list columns as column>
   /**
    *
        <#if column.desc??>
    * ${column.desc}
        </#if>
    */
        <#if column.fieldName??>
    @Field("${column.fieldName}")
        </#if>
    private ${column.javaType} ${column.name};

</#list>
</#if>
}
