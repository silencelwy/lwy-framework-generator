package io.github.silencelwy.generator.table.dto;

import lombok.Data;

import java.util.List;

/**
 * @author mb.wang
 * @version V1.0
 * @Package io.github.silencelwy.generator.table.dto
 * @Description: 与主表关联的表
 * @date 2018/5/22 15:30
 */
@Data
public class AssociativeTableDto {
    private TableRelationalMappingType relationalMappingType;
    private List<TableDto> associativeTables;
}
