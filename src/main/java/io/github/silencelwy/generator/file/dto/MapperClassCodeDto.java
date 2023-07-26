package io.github.silencelwy.generator.file.dto;

import io.github.silencelwy.generator.table.dto.AssociativeTableDto;
import io.github.silencelwy.generator.table.dto.ColumnDto;
import io.github.silencelwy.generator.table.dto.TableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MapperClassCodeDto extends ClassCodeDto{
    private TableDto tableDto;
    //自定义columns
    private List<ColumnDto> updateColumns;
    private List<ColumnDto> insertColumns;
    private List<ColumnDto> selectColumns;
    private AssociativeTableDto associativeTableDto;

}
