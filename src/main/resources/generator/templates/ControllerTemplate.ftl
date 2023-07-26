package ${basePackage}.${modelName}.controller;
<#assign model = "${ clsNamePrefix?cap_first }Model"/>
<#assign modelVal = "${ clsNamePrefix?uncap_first }"/>
<#assign serviceVal = "${ clsNamePrefix?uncap_first }Service"/>

import ${basePackage}.${modelName}.model.${model};
import ${basePackage}.${modelName}.service.I${clsNamePrefix?cap_first}Service;
import cn.com.flaginfo.framework.webmvc.utils.ResultGeneratorUtil;
import cn.com.flaginfo.framework.webmvc.aop.resubmit.ReSubmit;
import cn.com.flaginfo.framework.vo.QueryVO;
import cn.com.flaginfo.framework.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import cn.com.flaginfo.framework.webmvc.request.annotation.MultiRequestBody;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author ${author}
 * @version V1.0
 * @Package ${basePackage}.${modelName}.controller
 * @Description: ${desc}
 * @date ${.now}
 */
@RestController
@RequestMapping("${tableName?replace("_","/")}")
@Slf4j
public class ${clsNamePrefix?cap_first}Controller {
    @Autowired
    private I${clsNamePrefix?cap_first}Service ${serviceVal};

<#if generateMethods?contains("R") >

    @RequestMapping(value = "query",method = RequestMethod.POST)
    public ResponseEntity<ResultVO<List<${model}>>> query(@MultiRequestBody QueryVO<${model}> queryVO) {
        ${model} params = queryVO.getParams();
        return ResponseEntity.ok(this.${serviceVal}.query(params, queryVO.getPage(), queryVO.getPageSize(),queryVO.getSort(),null));
    }

    @RequestMapping(value = "getAll")
    public ResponseEntity<ResultVO<List<${model}>>> getAll() {
        return ResponseEntity.ok(ResultGeneratorUtil.success(this.${serviceVal}.getAll()));
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ResponseEntity<ResultVO<${model}>> get(${model} ${modelVal }) {

         return ResponseEntity.ok(ResultGeneratorUtil.success(this.${serviceVal}.get(${modelVal },null)));
    }
</#if>

<#if generateMethods?contains("U") >
    @ReSubmit
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseEntity<ResultVO<String>> update(@MultiRequestBody ${model} ${modelVal }) {

        return ResponseEntity.ok(this.${serviceVal}.update(${modelVal }));
    }
</#if>

<#if generateMethods?contains("C") >
    @ReSubmit
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<ResultVO<String>> add(@MultiRequestBody ${model} ${modelVal }) {

        return ResponseEntity.ok(this.${serviceVal}.insert(${modelVal }));
    }
</#if>

<#if generateMethods?contains("D") >
    @ReSubmit
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResultVO<String>> delete(@PathVariable ${primaryKey.javaType} id) {

         return ResponseEntity.ok(this.${serviceVal}.delete(id));
    }
</#if>

<#if generateMethods?contains("B") >
    @ReSubmit
    @RequestMapping(value = "delete/batch", method = RequestMethod.DELETE)
    public ResponseEntity<ResultVO<String>> delete(${primaryKey.javaType}... ids) {

         return ResponseEntity.ok(this.${serviceVal}.delete(null,ids));
    }
</#if>
}