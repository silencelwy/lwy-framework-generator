package ${basePackage}.${modelName}.service.impl;
<#assign model = "${ clsNamePrefix?cap_first }Model"/>
<#assign modelVal = "${ clsNamePrefix?uncap_first }"/>

import cn.com.flaginfo.framework.vo.PageVO;
import org.apache.commons.collections.CollectionUtils;
import ${basePackage}.${modelName}.model.${model};
import ${basePackage}.${modelName}.mapper.I${ clsNamePrefix?cap_first }Mapper;
import ${basePackage}.${modelName}.service.I${ clsNamePrefix?cap_first }Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.beanutils.BeanUtils;
import cn.com.flaginfo.framework.id.IdGenerator;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import cn.com.flaginfo.framework.webmvc.utils.ResultGeneratorUtil;
import cn.com.flaginfo.framework.vo.ResultVO;
import cn.com.flaginfo.framework.constant.ResultCode;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Set;


import java.lang.reflect.InvocationTargetException;
/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelName}.service
 * @Description: ${desc}
 * @date ${.now}
 */
@Service
@Slf4j
public class ${clsNamePrefix?cap_first}Service implements I${ clsNamePrefix?cap_first }Service{
<#assign mapperVal = "${ clsNamePrefix ?uncap_first}Mapper"/>
    @Autowired
    private I${ clsNamePrefix?cap_first }Mapper ${mapperVal};
<#if generateMethods?contains("R") >

    @Override
    public ResultVO<List<${model}>> query(${model} ${modelVal },int page,int pageSize,String sort,Consumer<${model}> consumer){
        PageList<${model}> list = ${mapperVal}.query(${modelVal},new PageBounds(page, pageSize, Order.formString(sort)));

        if(consumer!=null&& CollectionUtils.isNotEmpty(list)){
            list.forEach(${modelVal }Temp -> consumer.accept(${modelVal }Temp));
        }

        ResultVO<List<${model}>> resultVO = ResultGeneratorUtil.success(list);
        resultVO.setPage(PageVO.builder()
        .page(list.getPaginator().getPage())
        .pageSize(pageSize)
        .sort(sort)
        .total(list.getPaginator().getTotalCount())
        .build());

        return resultVO;
    }


    @Override
    public List<${model}> getAll(){
        PageList<${model}> list = ${mapperVal}.query(null,new PageBounds(1, Integer.MAX_VALUE, Order.formString("")));
        return list;
    }

    @Override
    public ${ model} get(${model} ${modelVal},Consumer<${model}> consumer) {
        ${model} ${modelVal}Temp = ${mapperVal}.get(${modelVal});

        if(consumer!=null){
            consumer.accept(${modelVal}Temp);
        }

        return ${modelVal}Temp;
    }

    @Override
    public ${ model} get(${primaryKey.javaType} id,Consumer<${model}> consumer) {
        ${model} ${modelVal} = new ${model}();
        ${modelVal}.set${primaryKey.name?cap_first}(id);
        return get(${modelVal},consumer);
    }
</#if>

<#if generateMethods?contains("D") >
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO<String> delete(Predicate<${primaryKey.javaType}> filter,${primaryKey.javaType}... ids) {

        if(ids == null){
            return ResultGeneratorUtil.error(ResultCode.PARAMETERS_ERROR);
        }

            if(filter!=null){
                Set<${primaryKey.javaType}> idsTemp = Arrays.stream(ids).filter(id -> filter.test(id)).collect(Collectors.toSet());
                ${mapperVal}.delete(idsTemp.toArray(new ${primaryKey.javaType}[idsTemp.size()]));
                return ResultGeneratorUtil.success(ResultCode.SUCCESS.desc());
            }else{
                ${mapperVal}.delete(ids);
                return ResultGeneratorUtil.success(ResultCode.SUCCESS.desc());
            }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO<String> delete(${primaryKey.javaType} id) {

        return delete(id);
    }
</#if>

<#if generateMethods?contains("C") >
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO<String> insert(${model} ${modelVal}) {
        if(${modelVal} == null){
            return ResultGeneratorUtil.error(ResultCode.PARAMETERS_ERROR);
        }

        try {
            BeanUtils.setProperty(${modelVal},"${primaryKey.name?uncap_first}",IdGenerator.getInstance().generateId());
        } catch (IllegalAccessException e) {
           throw  new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw  new RuntimeException(e);
        }

        ${mapperVal}.insert(${modelVal});

        return ResultGeneratorUtil.success(ResultCode.SUCCESS.desc());
    }
</#if>

<#if generateMethods?contains("B") >
    @Transactional(rollbackFor = Exception.class)
    @Override
	public ResultVO<String> batchInsert(List<${model}> ${modelVal}List){
        if(${modelVal}List == null){
            return ResultGeneratorUtil.error(ResultCode.PARAMETERS_ERROR);
        }

        for(${model} model:${modelVal}List){
            try {
                BeanUtils.setProperty(model,"${primaryKey.name?uncap_first}",IdGenerator.getInstance().generateId());
            } catch (IllegalAccessException e) {
                throw  new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw  new RuntimeException(e);
            }
        }

        ${mapperVal}.batchInsert(${modelVal}List);
        return ResultGeneratorUtil.success(ResultCode.SUCCESS.desc());
    }
</#if>

<#if generateMethods?contains("U") >
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO<String> update(${ model} ${modelVal}) {
        if(${modelVal} == null ||${modelVal}.get${primaryKey.name?cap_first}() == null ){
            return ResultGeneratorUtil.error(ResultCode.PARAMETERS_ERROR);
        }
        ${mapperVal}.update(${ modelVal});
        return ResultGeneratorUtil.success(ResultCode.SUCCESS.desc());
    }
</#if>
}