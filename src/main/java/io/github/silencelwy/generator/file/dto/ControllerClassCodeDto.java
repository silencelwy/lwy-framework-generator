package io.github.silencelwy.generator.file.dto;

import io.github.silencelwy.generator.table.dto.ColumnDto;
import lombok.Data;

@Data
public class ControllerClassCodeDto extends ClassCodeDto{
    private ColumnDto primaryKey;
    private String controlerUrl;

}
