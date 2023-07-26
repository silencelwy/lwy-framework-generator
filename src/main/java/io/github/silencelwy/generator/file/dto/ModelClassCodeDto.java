package io.github.silencelwy.generator.file.dto;

import io.github.silencelwy.generator.table.dto.AssociativeTableDto;
import io.github.silencelwy.generator.table.dto.TableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ModelClassCodeDto extends ClassCodeDto{
    private TableDto tableDto;
    private AssociativeTableDto associativeTableDto;
}
