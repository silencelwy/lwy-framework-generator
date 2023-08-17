package io.github.silencelwy.generator.table.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssociativeTableDto {
    private TableRelationalMappingType relationalMappingType;
    private List<TableDto> associativeTables;
}
