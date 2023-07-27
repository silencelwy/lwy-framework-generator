package ${basePackage}.${modelName}.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;

/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelName}.model
 * @Description: code by codeGenerator
 * @date ${.now}
 */
@NoArgsConstructor
@Getter
@Setter
public class ${model}Excel {
<#if columns?? && (columns?size > 0) >
<#list columns as column>
   /**
    *
        <#if column.desc??>
    * ${column.desc}
        </#if>
    */
    @ExcelProperty("${column.desc}")
    private ${column.javaType} ${column.name};

</#list>
</#if>

    @ExcelProperty("更新时间")
    private Date utime;

    @ExcelProperty("创建时间")
    private Date ctime;
}
