package io.github.silencelwy.generator.code.service.impl;

import io.github.silencelwy.generator.code.dto.CodeDto;
import io.github.silencelwy.generator.code.service.ICodeGeneratorService;
import io.github.silencelwy.generator.factory.TemplateCodeFactory;
import io.github.silencelwy.generator.file.dto.ControllerClassCodeDto;
import io.github.silencelwy.generator.table.dto.TableDto;
import io.github.silencelwy.generator.table.service.ITableService;
import io.github.silencelwy.generator.table.service.impl.TableService;

import java.sql.SQLException;



public class ControllerCodeGeneratorService implements ICodeGeneratorService {
    private ITableService tableService = new TableService();

    @Override
    public void generator(CodeDto codeDto) {
        try {
            TableDto tableDto = tableService.getColumnsByTable(codeDto.getTable(),codeDto.getSchema());

            ControllerClassCodeDto classCodeDto = new ControllerClassCodeDto();

            toChildClassCodeDto(codeDto,tableDto,classCodeDto);
            classCodeDto.setPrimaryKey(tableDto.getPrimaryKey());

            TemplateCodeFactory.getTemplateCodeService(classCodeDto.getArchitectureCode()).generatedFile(classCodeDto);
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }

    }
}
