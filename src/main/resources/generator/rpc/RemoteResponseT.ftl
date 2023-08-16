package ${basePackage}.${modelPackage}.rpc.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ${model}RpcResponse implements Serializable {
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

        private boolean del;
        private Date utime;
        private Date ctime;
}
