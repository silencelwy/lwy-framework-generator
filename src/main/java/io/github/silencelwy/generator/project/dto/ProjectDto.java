package io.github.silencelwy.generator.project.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author mb.wang
 * @version V1.0
 * @Package io.github.silencelwy.generator.dto
 * @Description: 工程配置
 * @date 2018/5/18 09:37
 */
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

    /**
     * @Fields field : 代码存放路径。默认存储到项目的基础路径
     * @author mb.wang
     * @date 2018/5/18 14:29
     */
    private String projectPath = System.getProperty("user.dir");
    /**
     * @Fields field : 模板路径
     * @author mb.wang
     * @date 2018/5/18 14:31
     */
    private String templateFilePath = projectPath + "/src/main/resources/generator/templates";
    private boolean templateCustom;
    /**
     * @Fields field : 生成java文件存放路径
     * @author mb.wang
     * @date 2018/5/18 14:32
     */
    private String javaPath = "/src/main/java";
    private String basePackage = "cn.com.flaginfo";
    private String author = System.getProperty("user.name");
    private String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    /**
     * @Fields field : 生成更新sql时，无需修改的通用字段可在这里排除，多个字段逗号分隔
     * @author mb.wang
     * @date 2018/5/18 14:32
     */
    private List<String> exclusionUpdateFields;
    /**
     * @Fields field : 生成新增sql时，无需插入的通用字段可在这里排除，多个字段逗号分隔
     * @author mb.wang
     * @date 2018/5/18 14:32
     */
    private List<String> exclusionInsertFields;
    /**
     * @Fields field : 生成查询sql时，无需查询的通用字段可在这里排除，多个字段逗号分隔
     * @author mb.wang
     * @date 2018/5/18 14:32
     */
    private List<String> exclusionSelectFields;
    /**
     * @Fields field : 生成实体类时，无需生成的通用字段可在这里排除，多个字段逗号分隔
     * @author mb.wang
     * @date 2018/5/18 14:32
     */
    private List<String> exclusionModelFields;

//    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);//生成的Service存放路径
//    private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_IMPL_PACKAGE);//生成的Service实现存放路径
//    private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);//生成的Controller存放路径

}
