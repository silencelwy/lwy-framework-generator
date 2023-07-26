package ${basePackage}.${modelName}.service;
<#assign clsName = "I${ clsNamePrefix?cap_first }Service"/>
<#assign model = "${ clsNamePrefix?cap_first }Model"/>
<#assign modelVal = "${ clsNamePrefix?uncap_first }"/>
import ${basePackage}.${modelName}.model.${model};
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import cn.com.flaginfo.framework.vo.ResultVO;

/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelName}.service
 * @Description: ${desc}
 * @date ${.now}
 */
public interface ${clsName} {
<#if generateMethods?contains("R") >
    ResultVO<List<${model}>> query(${model} ${modelVal },int page,int pageSize,String sort,Consumer<${model}> consumer);

    List<${model}> getAll();

    ${ model } get(${model} ${modelVal},Consumer<${model}> consumer);

    ${ model } get(${primaryKey.javaType} id,Consumer<${model}> consumer);

</#if>

<#if generateMethods?contains("D") >
    ResultVO<String> delete(Predicate<${primaryKey.javaType}> filter,${primaryKey.javaType}... ids);
    ResultVO<String> delete(${primaryKey.javaType} id);
</#if>

<#if generateMethods?contains("C") >
    ResultVO<String> insert(${model} ${ modelVal});
</#if>

<#if generateMethods?contains("B") >
    ResultVO<String> batchInsert(List<${model}> ${modelVal}List);
</#if>

<#if generateMethods?contains("U") >
    ResultVO<String> update(${model} ${modelVal});
</#if>
}