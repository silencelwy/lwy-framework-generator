package io.github.silencelwy.generator.code.service;

import io.github.silencelwy.generator.code.dto.CodeDto;
import io.github.silencelwy.generator.file.dto.ArchitectureCode;
import io.github.silencelwy.generator.file.dto.ClassCodeDto;
import io.github.silencelwy.generator.file.service.ITemplateCodeService;
import io.github.silencelwy.generator.project.config.ProjectConfig;
import io.github.silencelwy.generator.table.dto.ColumnDto;
import io.github.silencelwy.generator.table.dto.TableDto;
import io.github.silencelwy.generator.utils.UnderlineOrCamelUtil;

import java.util.List;
import java.util.stream.Collectors;

public interface ICodeGeneratorService {
    String PRIMARY_KEY = "id";

    void generator(CodeDto codeDto);

    /**
     * 通用参数初始化
     * @author mb.wang
     * @date 2018/5/21 09:05
     * @param tableDto
     * @param codeDto CodeDto
     * @param classCodeDto ClassCodeDto
     * @return void
     */
    default void toChildClassCodeDto(CodeDto codeDto, TableDto tableDto, ClassCodeDto classCodeDto) {
        final String methodsTmp = (codeDto.getMethods()==null||"".equals(codeDto.getMethods().trim()))?ITemplateCodeService.METHODS:codeDto.getMethods().toUpperCase();

        classCodeDto.setTableName(tableDto.getName());
        if(!"".equals(ProjectConfig.getProjectConfig().getProjectDto().getTablePrefix())){
            tableDto.setName(tableDto.getName().replaceFirst(ProjectConfig.getProjectConfig().getProjectDto().getTablePrefix(),""));
        }

        classCodeDto.setGenerateMethods(methodsTmp);
        classCodeDto.setArchitectureCode(codeDto.getArchitectureCode());
        classCodeDto.setModelName(codeDto.getModelName());
        classCodeDto.setFullPackage(generatedPackage(codeDto.getModelName(),classCodeDto.getArchitectureCode()));
        classCodeDto.setClsNamePrefix(tableDto.getClsNamePrefix());

        classCodeDto.setFileName(generatedFileName(classCodeDto.getClsNamePrefix(),classCodeDto.getArchitectureCode()));
        classCodeDto.setDesc(tableDto.getRemarks());

    }

    /**
     * 过滤掉columns中包含exclusionFields的数据
     * @author mb.wang
     * @date 2018/7/5 10:12
     * @param tableDto  表
     * @param  exclusionFields 需要过滤的属性
     * @return List<ColumnDto>
     */
    default List<ColumnDto> exclusionFields(TableDto tableDto, List<String> exclusionFields){
        return tableDto.getColumns().stream().filter(c->{
            boolean isExclusion = true;
            //排除主键、Field
            if(exclusionFields.contains(c.getJdbcName())
                    ||(exclusionFields.contains(PRIMARY_KEY)&&c.isPrimaryKey())){
                isExclusion = false;
            }

            return isExclusion;
        }).collect(Collectors.toList());
    }

    /**
     * 生成package
     * @author mb.wang
     * @date 2018/5/21 09:05
     * @param modelName
     * @param architectureCode 架构分层(model/dto/vo/mapper/service/controller)
     * @return java.lang.String
     */
    default String generatedPackage(String modelName, ArchitectureCode architectureCode){
        return ProjectConfig.getProjectConfig().getProjectDto().getBasePackage()+"."+modelName+"."+architectureCode.code();
    }
    /**
     * 文件名
     * @author mb.wang
     * @date 2018/5/21 09:05
     * @param clsNamePrefix
     * @param architectureCode 架构分层(model/dto/vo/mapper/service/controller)
     * @return java.lang.String
     */
    default String generatedFileName(String clsNamePrefix, ArchitectureCode architectureCode){
        return clsNamePrefix+UnderlineOrCamelUtil.underline2Camel(architectureCode.code(),false)+".java";
    }
}
