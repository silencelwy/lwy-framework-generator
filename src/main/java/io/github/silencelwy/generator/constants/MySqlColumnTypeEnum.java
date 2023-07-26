package io.github.silencelwy.generator.constants;

public enum MySqlColumnTypeEnum {

    TINYINT("tinyint", "int类型"),
    INT("int", "int类型"),
    BIGINT("bigint", "bigint类型"),
    DATE("date", "日期类型"),
    VARCHAR_MIN("varchar_min", "小字符串类型"),//32位
    VARCHAR("varchar", "正常字符串类型"),//64位
    VARCHAR_BIG("varchar_big", "大字符串类型"),//1000
    TEXT("text", "文本类型"),
    BOOLEAN("boolean", "布尔类型"); // Represented as INT for BOOLEAN

    private String code;
    private String desc;

    MySqlColumnTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
