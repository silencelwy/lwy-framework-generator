package ${basePackage}.${modelPackage}.rpc.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ${model}RpcRequest implements Serializable {
    private String id;
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
