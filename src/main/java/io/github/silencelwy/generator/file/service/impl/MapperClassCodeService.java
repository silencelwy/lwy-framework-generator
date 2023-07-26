package io.github.silencelwy.generator.file.service.impl;

import io.github.silencelwy.generator.file.dto.MapperClassCodeDto;
import io.github.silencelwy.generator.file.service.FreeMakerTemplate;
import io.github.silencelwy.generator.file.service.ITemplateCodeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapperClassCodeService implements ITemplateCodeService<MapperClassCodeDto> {
    private  static final String MAPPER_INTERFACE_TEMPLATE_NAME = "MapperTemplate.ftl";
    private  static final String MAPPER_SQLMAPPER_TEMPLATE_NAME = "SqlMapperTemplate.ftl";

    @Override
    public void generatedFile(MapperClassCodeDto modelClassCodeDto) {
        long startTime = System.currentTimeMillis();
        log.info("生成表{}的Mapper开始",modelClassCodeDto.getModelName());

        final String srcFileName = modelClassCodeDto.getFileName();
        modelClassCodeDto.setFileName("I"+srcFileName);
        FreeMakerTemplate.getFreeMakerTemplate().generateFile(MAPPER_INTERFACE_TEMPLATE_NAME,modelClassCodeDto);

        modelClassCodeDto.setFileName("I"+srcFileName.replaceAll(".java",".xml"));
        FreeMakerTemplate.getFreeMakerTemplate().generateFile(MAPPER_SQLMAPPER_TEMPLATE_NAME,modelClassCodeDto);

        log.info("生成表{}的Mapper结束，耗时：{}(ms)",modelClassCodeDto.getModelName(),(System.currentTimeMillis()-startTime));

    }
}
