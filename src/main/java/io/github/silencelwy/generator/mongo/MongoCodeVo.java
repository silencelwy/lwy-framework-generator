package io.github.silencelwy.generator.mongo;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author zhengmng
 */
@Data
@Builder
public class MongoCodeVo {
    private String model;
    private String basePackage;
    private String modelName;
    private String modelPath;
    private String collectionName;
    private String author;

    private String filePath;

    private String mPath;
    private String fileName;

    private List<ColumnVo> columns;

    public void buildPath(){
        String[] packages = StringUtils.split(basePackage,".");
        this.filePath = System.getProperty("user.dir") + "/src/main/java/" + String.join( "/",packages ) + "/" + modelName;
    }

}
