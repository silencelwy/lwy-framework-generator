package io.github.silencelwy.generator.mysql;

import io.github.silencelwy.generator.code.dto.CodeDto;
import io.github.silencelwy.generator.code.modelDefine.ModelDefine;
import io.github.silencelwy.generator.factory.CodeGeneratorFactory;
import io.github.silencelwy.generator.file.dto.ArchitectureCode;
import io.github.silencelwy.generator.file.service.ITemplateCodeService;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Arrays;

@Slf4j
public final class MysqlCodeGenerator {

    private static final MysqlCodeGenerator  instance = new MysqlCodeGenerator();

    public static MysqlCodeGenerator getInstance(){
        return instance;
    }
    private MysqlCodeGenerator() {
    }

    public void generator(ModelDefine modelDefine) {


    }
    /**
     * 默认生成所有代码
     *
     * @param schema
     * @param modelName
     * @param tables
     * @return void
     * @throws
     * @author mb.wang
     * @date 2018/5/22 17:54
     */
    public void generator(final String schema, final String modelName, final String... tables) {
        long startTime = System.currentTimeMillis();
        log.info("[{}]生成开始", modelName);
        generator(schema, modelName,  ITemplateCodeService.METHODS, tables);
        log.info("[{}]生成耗时{}(ms)", modelName, System.currentTimeMillis() - startTime);
    }

    /**
     * 默认生成所有代码
     *
     * @param schema
     * @param modelName
     * @param methods   CRUDB
     * @param tables
     * @return void
     * @throws
     * @author mb.wang
     * @date 2018/5/22 17:54
     */
    public void generator(final String schema, final String modelName, final String methods, final String... tables) {
        long startTime = System.currentTimeMillis();
        log.info("[{}]生成开始", modelName);

        Arrays.stream(tables).parallel().forEach(table -> {
            generator(schema, modelName, table, methods, ArchitectureCode.CONTROLLER, ArchitectureCode.MODEL, ArchitectureCode.SERVICE, ArchitectureCode.MAPPER);
        });
        log.info("[{}]生成耗时{}(ms)", modelName, System.currentTimeMillis() - startTime);
    }

    /**
     * 模块代码生成。
     *
     * @param schema
     * @param modelName
     * @param table
     * @param architectureCodes 为空，表示生成所有代码，需要生成特定代码，传入对应的code即可
     * @return void
     * @throws SQLException, InstantiationException, IllegalAccessException
     * @author mb.wang
     * @date 2018/5/20 09:48
     */
    public void generator(final String schema, final String modelName, final String table, final String methods, ArchitectureCode... architectureCodes) {
        log.info("[{}]生成{}对应模块开始", modelName, table);

        long startTime = System.currentTimeMillis();

        for (ArchitectureCode architectureCode : Arrays.asList(architectureCodes)) {
            CodeGeneratorFactory.getCodeGeneratorService(architectureCode).generator(new CodeDto(schema, modelName, table, methods, architectureCode));
        }

        log.info("[{}]生成{}对应模块耗时{}(ms)", modelName, table, System.currentTimeMillis() - startTime);
    }
}
