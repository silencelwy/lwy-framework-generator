package io.github.silencelwy.generator.factory;

import io.github.silencelwy.generator.file.dto.ArchitectureCode;
import io.github.silencelwy.generator.file.service.ITemplateCodeService;
import io.github.silencelwy.generator.file.service.impl.ControllerClassCodeService;
import io.github.silencelwy.generator.file.service.impl.MapperClassCodeService;
import io.github.silencelwy.generator.file.service.impl.ModelClassCodeService;
import io.github.silencelwy.generator.file.service.impl.ServiceClassCodeService;

public class TemplateCodeFactory {

    public static ITemplateCodeService getTemplateCodeService(ArchitectureCode architectureCode) {
        ITemplateCodeService templateCodeService = null;

        switch (architectureCode) {
            case CONTROLLER:
                templateCodeService = new ControllerClassCodeService();
                break;
            case MODEL:
                templateCodeService = new ModelClassCodeService();
                break;
            case SERVICE:
                templateCodeService = new ServiceClassCodeService();
                break;
            case MAPPER:
                templateCodeService = new MapperClassCodeService();
                break;
            default:
                break;
        }

        return templateCodeService;
    }
}
