package io.github.silencelwy.generator.code.modelDefine;

public enum ModelTypeEnum {
    MONGODB("mongodb"),
    MYSQL("mysql");


    private final String type;

    ModelTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
