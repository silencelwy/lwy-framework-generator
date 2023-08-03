package io.github.silencelwy.generator.mongo;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.github.silencelwy.generator.code.modelDefine.FieldDefine;
import io.github.silencelwy.generator.code.modelDefine.FieldTypeEnum;
import io.github.silencelwy.generator.code.modelDefine.ModelDefine;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动生成代码
 * 自动增加字段注解
 * 自动生成rap json格式
 *
 * @author zhengmng
 */
@Slf4j
public final class MongoCodeGenerator {

    private static final MongoCodeGenerator mongoCodeGenerator = new MongoCodeGenerator();

    public static MongoCodeGenerator getInstance() {
        return mongoCodeGenerator;
    }

    private MongoCodeGenerator() {

    }

    public void generator(ModelDefine modelDefine) {
        log.info("代码生成开始");
        String collName = CollUtil.join(splitByUp(modelDefine.getModel()), "_");
        MongoCodeVo codeVo = MongoCodeVo.builder()
                .basePackage(modelDefine.getBackPackage())
                .model(StrUtil.upperFirst(modelDefine.getModel()))
                .modelName(modelDefine.getModelDesc())
                .modelPath(StrUtil.isBlank(modelDefine.getModelPath()) ? "/" + StringUtils.lowerCase(modelDefine.getModel()) : modelDefine.getModelPath())
                .modelPackage(StrUtil.isBlank(modelDefine.getModelPackage()) ? StringUtils.lowerCase(modelDefine.getModel()) : modelDefine.getModelPackage())
                .collectionName(collName)
                .author(System.getProperty("user.name"))
                .build();
        codeVo.buildPath();


        @NonNull List<FieldDefine> fieldDefineList = modelDefine.getFieldDefineList();
        if (CollUtil.isNotEmpty(fieldDefineList)) {
            codeVo.setColumns(CollStreamUtil.toList(fieldDefineList, fieldDefine -> ColumnVo.builder()
                    .name(fieldDefine.getField())
                    .fieldName(fieldDefine.getFieldName())
                    .desc(fieldDefine.getFieldDesc())
                    .javaType(fieldDefine.getFieldTypeEnum().getJavaType())
                    .supportExport(fieldDefine.isSupportExport())
                    .supportImport(fieldDefine.isSupportImport())
                    .build()));
        }
        codeInit(codeVo);
    }

    private void codeInit(MongoCodeVo codeVo) {

        long startTime = System.currentTimeMillis();
        //model
        codeVo.setMPath("/model");
        codeVo.setFileName(codeVo.getModel() + ".java");
        FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("ModelT.ftl", codeVo);

        //ExcelModel
        codeVo.setMPath("/excel");
        codeVo.setFileName(codeVo.getModel() + "Excel.java");
        FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("ModelExcelT.ftl", codeVo);

        //vo
        codeVo.setMPath("/vo");
        codeVo.setFileName(codeVo.getModel() + "Vo.java");
        FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("VoT.ftl", codeVo);

        //repository
        codeVo.setMPath("/service");
        codeVo.setFileName("I" + codeVo.getModel() + "Repository.java");
        FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("RepositoryT.ftl", codeVo);

        //IService
        codeVo.setFileName("I" + codeVo.getModel() + "Service.java");
        FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("IServiceT.ftl", codeVo);

        //service
        codeVo.setMPath("/service/impl");
        codeVo.setFileName(codeVo.getModel() + "ServiceImpl.java");
        FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("ServiceT.ftl", codeVo);


        //IFacade
        codeVo.setMPath("/facade");
        codeVo.setFileName("I" + codeVo.getModel() + "Facade.java");
        FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("IFacadeT.ftl", codeVo);

        //facade
        codeVo.setMPath("/facade/impl");
        codeVo.setFileName(codeVo.getModel() + "FacadeImpl.java");
        FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("FacadeT.ftl", codeVo);

        //controller
        codeVo.setMPath("/controller");
        codeVo.setFileName(codeVo.getModel() + "Controller.java");
        FreeMakerMongoTemplate.getFreeMakerTemplate().generateFile("ControllerT.ftl", codeVo);

        log.info("代码生成耗时{}(ms)", System.currentTimeMillis() - startTime);
    }

    private static List<String> splitByUp(String model) {
        char[] chars = model.toCharArray();
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (CharUtils.isAsciiAlphaUpper(c)) {
                if (sb.length() > 0) {
                    list.add(sb.toString());
                    sb = new StringBuilder();
                }
                sb.append((char) (c + 32));
            } else {
                sb.append(c);
            }
        }

        list.add(sb.toString());
        return list;
    }
}
