package io.github.silencelwy.generator.file.dto;

public enum ArchitectureCode {
    MODEL("model","实体bean"),
    EDAS_API("dto","生成EDAS API代码"),
    EDAS_PROVIDER("service","生成EDAS API代码"),
    VO("vo","UI展现层/对外接口层实体bean"),
    MAPPER("mapper","持久层"),
    SERVICE("service","业务层"),
    CONTROLLER("controller","控制层");

    private String code;
    private String desc;

    ArchitectureCode(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String code(){
        return code;
    }
    public String desc(){
        return desc;
    }
}
