package io.github.silencelwy.generator.code.dto;

import io.github.silencelwy.generator.file.dto.ArchitectureCode;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CodeDto {
    @NonNull
    private String schema;
    @NonNull
    private String modelName;
    @NonNull
    private String table;
    @NonNull
    private String methods;
    @NonNull
    private ArchitectureCode architectureCode;
}
