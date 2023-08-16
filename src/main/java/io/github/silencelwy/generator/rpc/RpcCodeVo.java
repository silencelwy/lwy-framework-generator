package io.github.silencelwy.generator.rpc;

import io.github.silencelwy.generator.mongo.ColumnVo;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author zhengmng
 */
@Data
@Builder
public class RpcCodeVo {
    private String model;
    private String basePackage;
    private String modelName;
    private String modelPath;
    private String modelPackage;
    private String collectionName;
    private Boolean needRpc = false;
    private Boolean needCache = false;
    private String author;

    private String filePath;

    private String mPath;
    private String fileName;

    private List<ColumnVo> columns;

    public void buildPath(){
        String[] packages = StringUtils.split(basePackage,".");
        this.filePath = System.getProperty("user.dir") + "/src/main/java/" + String.join( "/",packages ) + "/" + modelPackage;
    }

}
