package ${basePackage}.${modelPackage}.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ${model}Vo implements Serializable {

<#if columns?? && (columns?size > 0) >
<#list columns as column>
   /**
    *    <#if column.desc??>
    * ${column.desc}
        </#if>
    */
    private ${column.javaType} ${column.name};

</#list>
</#if>
}
