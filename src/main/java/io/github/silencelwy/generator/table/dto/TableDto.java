package io.github.silencelwy.generator.table.dto;

import io.github.silencelwy.generator.project.config.ProjectConfig;
import io.github.silencelwy.generator.project.dto.ProjectDto;
import io.github.silencelwy.generator.utils.UnderlineOrCamelUtil;
import lombok.Data;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class TableDto {
    @NonNull
    private String name;
    private String clsNamePrefix;
    private String type;
    private String remarks;
    private ColumnDto primaryKey;

    private List<ColumnDto> columns;

    public String getClsNamePrefix(){
        ProjectDto projectDto = ProjectConfig.getProjectConfig().getProjectDto();
        String nameTmp = name;
        if(StringUtils.isNotBlank(projectDto.getTablePrefix())){
            nameTmp = nameTmp.replaceFirst(projectDto.getTablePrefix(),"");
        }
        return UnderlineOrCamelUtil.underline2Camel(nameTmp,false);
    }
}
