package io.github.silencelwy.generator.project.config;

import io.github.silencelwy.generator.project.dto.ProjectDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.ArrayList;

/**
 * @author mb.wang
 * @version V1.0
 * @Package io.github.silencelwy.generator.config
 * @Description: 配置信息获取
 * @date 2018/5/19 19:28
 */
@Slf4j
public class ProjectConfig {
    private static PropertiesConfiguration propertiesConfiguration;

    private volatile static ProjectConfig projectConfig;

    private ProjectConfig() {
    }

    private static ProjectDto projectDto;

    public static ProjectConfig getProjectConfig() {
        if (projectConfig == null) {
            synchronized (ProjectConfig.class) {
                if (projectConfig == null) {
                    projectConfig = new ProjectConfig();
                }
            }
        }
        return projectConfig;
    }

    static {
        FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                new FileBasedConfigurationBuilder<PropertiesConfiguration>(PropertiesConfiguration.class)
                        .configure(new Parameters().properties()
                                .setURL(ProjectConfig.class.getClassLoader().getResource("generator.properties"))
                                .setThrowExceptionOnMissing(true)
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(','))
                                .setIncludesAllowed(false)
                                .setEncoding("UTF-8"));

        try {
            propertiesConfiguration = builder.getConfiguration();

            projectDto = new ProjectDto(propertiesConfiguration.getString("jdbc.url"),
                    propertiesConfiguration.getString("jdbc.diver.class-name"),
                    propertiesConfiguration.getString("jdbc.username"),
                    propertiesConfiguration.getString("jdbc.password"));
            projectDto.setTemplateCustom(propertiesConfiguration.getBoolean("template.custom", false));
            if(projectDto.isTemplateCustom()){
                projectDto.setTemplateFilePath(propertiesConfiguration.getString("template.path"));
            }

            projectDto.setTablePrefix(propertiesConfiguration.getString("table.prefix",""));

            projectDto.setExclusionInsertFields(propertiesConfiguration.getList(String.class,"exclusion.insert.fields",new ArrayList<>()));
            projectDto.setExclusionUpdateFields(propertiesConfiguration.getList(String.class,"exclusion.update.fields",new ArrayList<>()));
            projectDto.setExclusionSelectFields(propertiesConfiguration.getList(String.class,"exclusion.select.fields",new ArrayList<>()));
            projectDto.setExclusionModelFields(propertiesConfiguration.getList(String.class,"exclusion.model.fields",new ArrayList<>()));

            projectDto.setAuthor(propertiesConfiguration.getString("author", System.getProperty("user.name")));
            projectDto.setBasePackage(propertiesConfiguration.getString("base.package"));

        } catch (ConfigurationException e) {
            log.error("初始化模板生成配置文件失败！", e);
        }

    }

    public PropertiesConfiguration getPropertiesConfiguration() {
        return propertiesConfiguration;
    }

    public ProjectDto getProjectDto() {
        return projectDto;
    }

}
