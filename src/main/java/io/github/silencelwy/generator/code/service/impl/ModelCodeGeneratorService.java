package io.github.silencelwy.generator.code.service.impl;

import io.github.silencelwy.generator.code.dto.CodeDto;
import io.github.silencelwy.generator.code.service.ICodeGeneratorService;
import io.github.silencelwy.generator.factory.TemplateCodeFactory;
import io.github.silencelwy.generator.file.dto.ModelClassCodeDto;
import io.github.silencelwy.generator.project.config.ProjectConfig;
import io.github.silencelwy.generator.table.dto.AssociativeTableDto;
import io.github.silencelwy.generator.table.dto.TableDto;
import io.github.silencelwy.generator.table.service.ITableService;
import io.github.silencelwy.generator.table.service.impl.TableService;

import java.sql.SQLException;

public class ModelCodeGeneratorService implements ICodeGeneratorService {
    private ITableService tableService = new TableService();

    @Override
    public void generator(CodeDto codeDto) {
        try {
            TableDto tableDto = tableService.getColumnsByTable(codeDto.getTable(), codeDto.getSchema());

            ModelClassCodeDto classCodeDto = new ModelClassCodeDto();

            toChildClassCodeDto(codeDto,tableDto,classCodeDto);
            tableDto.setColumns(exclusionFields(tableDto,ProjectConfig.getProjectConfig().getProjectDto().getExclusionModelFields()));
            classCodeDto.setTableDto(tableDto);

            //关系表解析。model生成需要用到关联表信息
            AssociativeTableDto associativeTableDto = tableService.parseAssociativeTable(codeDto.getTable(), codeDto.getSchema());
            classCodeDto.setAssociativeTableDto(associativeTableDto);

            TemplateCodeFactory.getTemplateCodeService(classCodeDto.getArchitectureCode()).generatedFile(classCodeDto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
