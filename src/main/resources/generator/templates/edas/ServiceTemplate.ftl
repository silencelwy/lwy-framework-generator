package ${basePackage}.${modelName}.service.impl;
<#assign model = "${ clsNamePrefix?cap_first }dto"/>
<#assign modelVal = "${ clsNamePrefix?uncap_first }"/>

import ${basePackage}.${modelName}.dto.${model};
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.beanutils.BeanUtils;
import cn.com.flaginfo.framework.id.IdGenerator;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.alibaba.boot.hsf.annotation.HSFProvider;

import java.lang.reflect.InvocationTargetException;
/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelName}.service
 * @Description: ${desc}
 * @date ${.now}
 */
@HSFProvider(serviceInterface = ${clsNamePrefix?cap_first}Service.class)
@Slf4j
public class ${clsNamePrefix?cap_first}Service implements I${ clsNamePrefix?cap_first }Service{

<#if generateMethods?contains("R") >
    @Override
    public PageList<${model}> query(${model} ${modelVal},int page,int pageSize,String sort) {
        // TODO
        return null;
    }

    @Override
    public ${ model} get(${model} ${modelVal}) {
        // TODO
        return null;
    }
</#if>

<#if generateMethods?contains("D") >
    @Override
    public void delete(${primaryKey.javaType}... ids) {
        // TODO
    }
</#if>

<#if generateMethods?contains("C") >
    @Override
    public void insert(${model} ${modelVal}) {
        // TODO
    }
</#if>

<#if generateMethods?contains("B") >
    @Override
	public int batchInsert(List<${model}> ${modelVal}List){
        // TODO
       return 0;
    }
</#if>

<#if generateMethods?contains("U") >
    @Override
    public void update(${ model} ${modelVal}) {
        // TODO
    }
</#if>
}