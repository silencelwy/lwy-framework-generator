package io.github.silencelwy.generator.mongo;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhengmng
 */
@Data
@Builder
public class ColumnVo {
    private String name;
    private String javaType;
    private String fieldName;
    private String desc;
}
