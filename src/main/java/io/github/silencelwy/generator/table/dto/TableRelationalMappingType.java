package io.github.silencelwy.generator.table.dto;

/**
 * @author mb.wang
 * @version V1.0
 * @Package io.github.silencelwy.generator.table.dto
 * @Description: 关系映射
 * @date 2018/5/22 15:15
 */
public enum TableRelationalMappingType {
    /**
     * @Fields field : 单表
     * @author mb.wang
     * @date 2018/5/22 15:19
     */
    NONE("none"),
    /**
     * @Fields field : 一对一
     * @author mb.wang
     * @date 2018/5/22 15:19
     */
    ONE_TO_ONE("one_to_one"),
    /**
     * @Fields field : 一对多
     * @author mb.wang
     * @date 2018/5/22 15:19
     */
    ONE_TO_MORE("one_to_more");

    private String type;
    TableRelationalMappingType(String type){
        this.type =type;
    }

    public String getType(){
        return type;
    }
}
