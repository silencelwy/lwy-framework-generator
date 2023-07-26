package io.github.silencelwy.generator.file.service.impl;

import io.github.silencelwy.generator.file.dto.ModelClassCodeDto;
import io.github.silencelwy.generator.file.service.FreeMakerTemplate;
import io.github.silencelwy.generator.file.service.ITemplateCodeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModelClassCodeService implements ITemplateCodeService<ModelClassCodeDto> {
    private  static final String TEMPLATE_NAME = "ModelTemplate.ftl";
    @Override
    public void generatedFile(ModelClassCodeDto modelClassCodeDto) {
        long startTime = System.currentTimeMillis();
        log.info("生成表{}的MODEL实体类开始",modelClassCodeDto.getModelName());

        FreeMakerTemplate.getFreeMakerTemplate().generateFile(TEMPLATE_NAME,modelClassCodeDto);

        log.info("生成表{}的MODEL实体类结束，耗时：{}(ms)",modelClassCodeDto.getModelName(),(System.currentTimeMillis()-startTime));
    }
}
