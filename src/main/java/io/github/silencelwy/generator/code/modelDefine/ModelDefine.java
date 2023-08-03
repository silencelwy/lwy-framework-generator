package io.github.silencelwy.generator.code.modelDefine;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
public class ModelDefine {


    @NonNull
    private ModelTypeEnum modelTypeEnum;
    @NonNull
    private String model;
    @NonNull
    private String modelDesc;

//    @NonNull
    private String modelPath;

//    @NonNull
    private String modelPackage;

    @NonNull
    private String backPackage;
    private boolean needRpc;
    private boolean needCache;
    @NonNull
    private List<FieldDefine> fieldDefineList;

}
