package ${basePackage}.${modelName}.model;

import cn.com.flaginfo.framework.model.BaseModel;
import cn.com.flaginfo.framework.db.annotation.Primarykey;
<#if associativeTableDto??>
    <#if associativeTableDto.relationalMappingType.type == 'one_to_one' || associativeTableDto.relationalMappingType.type == 'one_to_more'>
        <#list associativeTableDto.associativeTables as associativeTable>
import ${basePackage}.${modelName}.model.${ associativeTable.clsNamePrefix?cap_first }Model;
        </#list>
    </#if>
</#if>

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelName}.model
 * @Description: ${desc}
 * @date ${.now}
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ${ clsNamePrefix?cap_first }Model extends BaseModel{
    <#if associativeTableDto??>
        <#if associativeTableDto.relationalMappingType.type == 'one_to_one'>
            <#list associativeTableDto.associativeTables as associativeTable>
     private ${ associativeTable.clsNamePrefix?cap_first }Model ${ associativeTable.clsNamePrefix?uncap_first }Model;
            </#list>
        <#elseif associativeTableDto.relationalMappingType.type == 'one_to_more'>
            <#list associativeTableDto.associativeTables as associativeTable>
     private List<${ associativeTable.clsNamePrefix?cap_first }Model> ${ associativeTable.clsNamePrefix?uncap_first }Models;
            </#list>
        </#if>

    </#if>

	<#list tableDto.columns as columnDto>
        <#if columnDto.name!='state' && columnDto.name!='stateDate'>
	/**
     * @Fields field : ${columnDto.remarks}
     * @author ${author}
     * @date ${.now}
     */
    <#if columnDto.name==tableDto.primaryKey.name>
     @Primarykey
    </#if>
	private ${columnDto.javaType} ${columnDto.name};
    </#if>
	</#list>

}