package io.github.silencelwy.generator.table.dto;


public enum TableRelationalMappingType {
    NONE("none"),
    ONE_TO_ONE("one_to_one"),
    ONE_TO_MORE("one_to_more");
    private String type;
    TableRelationalMappingType(String type){
        this.type =type;
    }

    public String getType(){
        return type;
    }
}
