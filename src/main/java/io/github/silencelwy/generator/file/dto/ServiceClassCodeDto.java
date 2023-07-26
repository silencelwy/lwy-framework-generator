package io.github.silencelwy.generator.file.dto;

import io.github.silencelwy.generator.table.dto.ColumnDto;
import lombok.Data;

@Data
public class ServiceClassCodeDto extends ClassCodeDto{
    private ColumnDto primaryKey;
}
