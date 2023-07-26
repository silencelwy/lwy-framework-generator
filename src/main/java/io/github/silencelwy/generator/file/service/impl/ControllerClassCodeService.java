package io.github.silencelwy.generator.file.service.impl;

import io.github.silencelwy.generator.file.dto.ControllerClassCodeDto;
import io.github.silencelwy.generator.file.service.FreeMakerTemplate;
import io.github.silencelwy.generator.file.service.ITemplateCodeService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ControllerClassCodeService implements ITemplateCodeService<ControllerClassCodeDto> {
    private  static final String CONTROLLER_TEMPLATE_NAME = "ControllerTemplate.ftl";

    @Override
    public void generatedFile(ControllerClassCodeDto modelClassCodeDto) {
        long startTime = System.currentTimeMillis();
        log.info("生成表{}的Controller开始",modelClassCodeDto.getModelName());

        FreeMakerTemplate.getFreeMakerTemplate().generateFile(CONTROLLER_TEMPLATE_NAME,modelClassCodeDto);

        log.info("生成表{}的Controller结束，耗时：{}(ms)",modelClassCodeDto.getModelName(),(System.currentTimeMillis()-startTime));

    }
}
