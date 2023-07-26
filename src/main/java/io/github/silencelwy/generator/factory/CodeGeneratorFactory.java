package io.github.silencelwy.generator.factory;

import io.github.silencelwy.generator.code.service.ICodeGeneratorService;
import io.github.silencelwy.generator.code.service.impl.ControllerCodeGeneratorService;
import io.github.silencelwy.generator.code.service.impl.MapperCodeGeneratorService;
import io.github.silencelwy.generator.code.service.impl.ModelCodeGeneratorService;
import io.github.silencelwy.generator.code.service.impl.ServiceCodeGeneratorService;
import io.github.silencelwy.generator.file.dto.ArchitectureCode;

public class CodeGeneratorFactory {

    public static ICodeGeneratorService getCodeGeneratorService(ArchitectureCode architectureCode) {
        ICodeGeneratorService codeGeneratorService = null;

        switch (architectureCode) {
            case CONTROLLER:
                codeGeneratorService = new ControllerCodeGeneratorService();
                break;
            case MODEL:
                codeGeneratorService = new ModelCodeGeneratorService();
                break;
            case SERVICE:
                codeGeneratorService = new ServiceCodeGeneratorService();
                break;
            case MAPPER:
                codeGeneratorService = new MapperCodeGeneratorService();
                break;
            default:
                break;
        }

        return codeGeneratorService;
    }
}
