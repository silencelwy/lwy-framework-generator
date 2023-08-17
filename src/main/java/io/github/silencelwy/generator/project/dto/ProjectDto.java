package io.github.silencelwy.generator.project.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Data
@RequiredArgsConstructor
public class ProjectDto {
    @NonNull
    private String jdbcUrl;
    @NonNull
    private String jdbcDiverClassName;
    @NonNull
    private String jdbcUsername;
    @NonNull
    private String jdbcPassword;

    private String tablePrefix;


    private String projectPath = System.getProperty("user.dir");

    private String templateFilePath = projectPath + "/src/main/resources/generator/templates";
    private boolean templateCustom;
    private String javaPath = "/src/main/java";
    private String basePackage = "cn.com.flaginfo";
    private String author = System.getProperty("user.name");
    private String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    private List<String> exclusionUpdateFields;
    private List<String> exclusionInsertFields;
    private List<String> exclusionSelectFields;
    private List<String> exclusionModelFields;

//    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);//生成的Service存放路径
//    private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_IMPL_PACKAGE);//生成的Service实现存放路径
//    private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);//生成的Controller存放路径

}
