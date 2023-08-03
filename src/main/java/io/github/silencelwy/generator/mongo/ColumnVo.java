package io.github.silencelwy.generator.mongo;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ColumnVo {
    private String name;
    private String javaType;
    private boolean supportImport;
    private boolean supportExport;
    private String fieldName;
    private String desc;
}
