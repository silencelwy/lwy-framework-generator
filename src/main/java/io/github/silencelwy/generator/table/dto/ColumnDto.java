package io.github.silencelwy.generator.table.dto;

import io.github.silencelwy.generator.utils.UnderlineOrCamelUtil;
import lombok.Data;

/**
 * @author mb.wang
 * @version V1.0
 * @Package io.github.silencelwy.generator.table.dto
 * @Description: //todo (用一句话描述该文件做什么)
 * @date 2018/5/18 16:38
 */

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
