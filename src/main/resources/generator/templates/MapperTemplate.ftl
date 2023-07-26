package ${basePackage}.${modelName}.mapper;

<#assign model = "${ clsNamePrefix?cap_first }Model"/>
<#assign modelVal = "${ clsNamePrefix?uncap_first }"/>
import ${basePackage}.${modelName}.model.${model};
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelName}.mapper
 * @Description: ${desc}
 * @date ${.now}
 */
@Mapper
public interface I${ clsNamePrefix?cap_first }Mapper {
<#if generateMethods?contains("C") >
	/**
	 * @Title: insert
	 * @Description: 新增数据
	 * @author ${author}
	 * @date ${.now}
	 * @param ${modelVal} ${model}
	 * @return int
	 */
	int insert(${model} ${modelVal});
</#if>

<#if generateMethods?contains("B") >
	/**
	 * @Title: batchInsert
	 * @Description: 批量增加数据
	 * @author ${author}
	 * @date ${.now}
	 * @param ${modelVal}List ${model}
	 * @return int
	 */
	int batchInsert(List<${model}> ${modelVal}List);
</#if>

<#if generateMethods?contains("D") >
	/**
	 * @Title: delete
	 * @Description: 根据id删除数据
	 * @author ${author}
	 * @date ${.now}
	 * @param ids
	 * @return int
	 */
	int delete(${tableDto.primaryKey.javaType}[] ids);
</#if>

<#if generateMethods?contains("U") >
	/**
	 * @Title: update
	 * @Description: 更新数据
	 * @author ${author}
	 * @date ${.now}
	 * @param ${modelVal} ${model}
	 * @return int
	 */
	int update(${model} ${modelVal});
</#if>

<#if generateMethods?contains("R") >
	/**
	 * @Title: query
	 * @Description: 分页获取数据
	 * @author ${author}
	 * @date ${.now}
	 * @param ${modelVal} ${model}
	 * @param pageBounds
	 * @return PageList<${model}>  返回类型
    */
    PageList<${model}> query(${model} ${modelVal}, PageBounds pageBounds);

        /**
        * @Title: get
        * @Description: 获取某个数据对象
	 	* @author ${author}
	 	* @date ${.now}
        * @param ${modelVal} ${model}
        * @return ${model}
        */
	${model} get(${model} ${modelVal});
</#if>
}