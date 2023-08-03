package io.github.silencelwy.generator;

import com.google.common.collect.Lists;
import io.github.silencelwy.generator.code.modelDefine.FieldDefine;
import io.github.silencelwy.generator.code.modelDefine.FieldTypeEnum;
import io.github.silencelwy.generator.code.modelDefine.ModelDefine;
import io.github.silencelwy.generator.code.modelDefine.ModelTypeEnum;
import org.junit.Test;

public class CodeGeneratorTest {

    @Test
    public void generatorTest(){
        CodeGenerator.getInstance().generator(Lists.newArrayList(
                ModelDefine.builder()
                        .backPackage("cn.com.flaginfo.test")
                        .model("crown")
//                        .modelPath("/crown")
                        .modelPackage("crown")
                        .modelDesc("人群包")
                        .needCache(true)
                        .needRpc(true)
                        .modelTypeEnum(ModelTypeEnum.MONGODB)
                        .fieldDefineList(Lists.newArrayList(FieldDefine.builder()
                                .field("nameTest")
                                .fieldDesc("姓名")
                                .fieldName("name_test")
                                .supportExport(true)
                                .supportImport(true)
                                .fieldTypeEnum(FieldTypeEnum.VARCHAR)
                                .build(),
                                FieldDefine.builder()
                                        .field("ageTest")
                                        .fieldDesc("年龄")
                                        .fieldName("age_test")
                                        .supportImport(true)
                                        .supportExport(true)
                                        .fieldTypeEnum(FieldTypeEnum.VARCHAR)
                                        .build()
                                ))
                        .build()
        ));
    }
}
