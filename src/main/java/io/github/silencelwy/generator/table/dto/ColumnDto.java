package io.github.silencelwy.generator.table.dto;

import io.github.silencelwy.generator.utils.UnderlineOrCamelUtil;
import lombok.Data;

@Data
public class ColumnDto {
    private String jdbcType;
    private String javaType;
    private int length;
    private String name;
    private String jdbcName;
    private boolean isPrimaryKey;
    //0:不许为空;1:可以为空；2.不确定是否为空
    private boolean nullable;
    private String remarks;
    private String defaultValue;
    private int order;

    public String getName(){
        return UnderlineOrCamelUtil.underline2Camel(jdbcName,true);
    }
}
