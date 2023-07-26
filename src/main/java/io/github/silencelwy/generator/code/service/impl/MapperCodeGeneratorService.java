package io.github.silencelwy.generator.code.service.impl;

import io.github.silencelwy.generator.code.dto.CodeDto;
import io.github.silencelwy.generator.code.service.ICodeGeneratorService;
import io.github.silencelwy.generator.factory.TemplateCodeFactory;
import io.github.silencelwy.generator.file.dto.ArchitectureCode;
import io.github.silencelwy.generator.file.dto.MapperClassCodeDto;
import io.github.silencelwy.generator.project.config.ProjectConfig;
import io.github.silencelwy.generator.table.dto.AssociativeTableDto;
import io.github.silencelwy.generator.table.dto.ColumnDto;
import io.github.silencelwy.generator.table.dto.TableDto;
import io.github.silencelwy.generator.table.service.ITableService;
import io.github.silencelwy.generator.table.service.impl.TableService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class MapperCodeGeneratorService implements ICodeGeneratorService {
    private ITableService tableService = new TableService();

    @Override
    public void generator(CodeDto codeDto) {
        try {
            TableDto tableDto = tableService.getColumnsByTable(codeDto.getTable(), codeDto.getSchema());

            MapperClassCodeDto classCodeDto = new MapperClassCodeDto();

            toChildClassCodeDto(codeDto,tableDto,classCodeDto);

            classCodeDto.setTableDto(tableDto);

            classCodeDto.setSelectColumns(exclusionFields(tableDto,ProjectConfig.getProjectConfig().getProjectDto().getExclusionSelectFields()));
            if(codeDto.getArchitectureCode()!=ArchitectureCode.EDAS_API){
                List<ColumnDto> list = exclusionFields(tableDto,ProjectConfig.getProjectConfig().getProjectDto().getExclusionInsertFields());
                Collections.sort(list, new Comparator<ColumnDto>() {
                    @Override
                    public int compare(ColumnDto o1, ColumnDto o2) {
                        return o1.getOrder()-o2.getOrder();
                    }
                });
                classCodeDto.setInsertColumns(list);

                classCodeDto.setUpdateColumns(exclusionFields(tableDto,ProjectConfig.getProjectConfig().getProjectDto().getExclusionUpdateFields()));
            }

            //关系表解析。mapper生成需要用到关联表信息
            AssociativeTableDto associativeTableDto = tableService.parseAssociativeTable(codeDto.getTable(), codeDto.getSchema());
            classCodeDto.setAssociativeTableDto(associativeTableDto);

            TemplateCodeFactory.getTemplateCodeService(classCodeDto.getArchitectureCode()).generatedFile(classCodeDto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
