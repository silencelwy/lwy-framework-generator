package io.github.silencelwy.generator.code.modelDefine;


import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class FieldDefine {
    @NonNull
    private String field;
    @NonNull
    private String fieldName;
    @NonNull
    private String fieldDesc;
    @NonNull
    private FieldTypeEnum fieldTypeEnum;

    private boolean supportImport;
    private boolean supportExport;
}
