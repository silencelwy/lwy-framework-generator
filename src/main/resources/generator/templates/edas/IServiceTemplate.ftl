package ${basePackage}.${modelName}.api;
<#assign clsName = "I${ clsNamePrefix?cap_first }Service"/>
<#assign model = "${ clsNamePrefix?cap_first }Model"/>
<#assign modelVal = "${ clsNamePrefix?uncap_first }"/>
import ${basePackage}.${modelName}.dto.${model};
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import java.util.List;

/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelName}.service
 * @Description: ${desc}
 * @date ${.now}
 */
public interface ${clsName} {
<#if generateMethods?contains("R") >
    PageList<${model}> query(${model} ${modelVal },int page,int pageSize,String sort);

    ${ model } get(${model} ${modelVal});
</#if>

<#if generateMethods?contains("D") >
    void delete(${primaryKey.javaType}... ids);
</#if>

<#if generateMethods?contains("C") >
    void insert(${model} ${ modelVal});
</#if>

<#if generateMethods?contains("B") >
	int batchInsert(List<${model}> ${modelVal}List);
</#if>

<#if generateMethods?contains("U") >
    void update(${model} ${modelVal});
</#if>
}