package io.github.silencelwy.generator.code.modelDefine;

import cn.hutool.core.util.StrUtil;

public enum FieldTypeEnum {
    TINYINT("tinyint", "tinyint类型"),
    INT("int", "int类型"),
    BIGINT("bigint", "bigint类型"),
    DATE("date", "日期类型"),
    VARCHAR_SMALL("varchar_small", "小字符串类型(32)"),
    VARCHAR("varchar", "字符串类型(256)"),
    VARCHAR_BIG("varchar_big", "大字符串类型(1000)"),
    TEXT("text", "文本类型"),
    BOOLEAN("boolean", "布尔类型"); // Represented as INT for BOOLEAN

    private final String type;
    private final String description;

    FieldTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }
    public String getJavaType() {
        if (StrUtil.equalsAny(type,"tinyint","int")){
            return "Integer";
        }else if (StrUtil.equalsAny(type,"bigint")){
            return "Long";
        }else if (StrUtil.contains(type,"varchar") || StrUtil.equals(type,"text")){
            return "String";
        }else if (StrUtil.equals(type,"date")){
            return "Date";
        }else if (StrUtil.equals(type,"boolean")){
            return "Boolean";
        }
        return "String";
    }

    public String getDescription() {
        return description;
    }
}
