package io.github.silencelwy.generator.file.dto;

import io.github.silencelwy.generator.project.config.ProjectConfig;
import lombok.Data;

import java.io.Serializable;

@Data
public class ClassCodeDto implements Serializable {
    private String basePackage = ProjectConfig.getProjectConfig().getProjectDto().getBasePackage();
    private String modelName;
    private String tableName;
    private String clsNamePrefix;

    private String fullPackage;
    /**
     * @Fields field : 模块名称。
     * @author mb.wang
     * @date 2018/5/19 18:36
     */
    private ArchitectureCode architectureCode;

    private String generateMethods;

    private String fileName;
    private String author = ProjectConfig.getProjectConfig().getProjectDto().getAuthor();
    private String desc;

    public String getDesc(){
        return "【代码生成器】"+desc;
    }
}
