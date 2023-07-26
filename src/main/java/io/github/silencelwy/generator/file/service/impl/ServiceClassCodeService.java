package io.github.silencelwy.generator.file.service.impl;

import io.github.silencelwy.generator.file.dto.ArchitectureCode;
import io.github.silencelwy.generator.file.dto.ServiceClassCodeDto;
import io.github.silencelwy.generator.file.service.FreeMakerTemplate;
import io.github.silencelwy.generator.file.service.ITemplateCodeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceClassCodeService implements ITemplateCodeService<ServiceClassCodeDto> {
    private  static final String SERVICE_INTERFACE_TEMPLATE_NAME = "IServiceTemplate.ftl";
    private  static final String SERVICE_TEMPLATE_NAME = "ServiceTemplate.ftl";

    @Override
    public void generatedFile(ServiceClassCodeDto modelClassCodeDto) {
        long startTime = System.currentTimeMillis();
        log.info("生成表{}的Service开始",modelClassCodeDto.getModelName());

        final String srcFileName = modelClassCodeDto.getFileName();

        if(modelClassCodeDto.getArchitectureCode()==ArchitectureCode.EDAS_API){
            modelClassCodeDto.setFileName("I"+srcFileName);
            FreeMakerTemplate.getFreeMakerTemplate().generateFile(SERVICE_INTERFACE_TEMPLATE_NAME,modelClassCodeDto);
        }else if(modelClassCodeDto.getArchitectureCode()==ArchitectureCode.EDAS_PROVIDER){
            modelClassCodeDto.setFileName(srcFileName);
            modelClassCodeDto.setFullPackage(modelClassCodeDto.getFullPackage()+".impl");
            FreeMakerTemplate.getFreeMakerTemplate().generateFile(SERVICE_TEMPLATE_NAME,modelClassCodeDto);
        }else{
            modelClassCodeDto.setFileName("I"+srcFileName);
            FreeMakerTemplate.getFreeMakerTemplate().generateFile(SERVICE_INTERFACE_TEMPLATE_NAME,modelClassCodeDto);

            modelClassCodeDto.setFileName(srcFileName);
            modelClassCodeDto.setFullPackage(modelClassCodeDto.getFullPackage()+".impl");
            FreeMakerTemplate.getFreeMakerTemplate().generateFile(SERVICE_TEMPLATE_NAME,modelClassCodeDto);
        }

        log.info("生成表{}的Service结束，耗时：{}(ms)",modelClassCodeDto.getModelName(),(System.currentTimeMillis()-startTime));

    }
}
