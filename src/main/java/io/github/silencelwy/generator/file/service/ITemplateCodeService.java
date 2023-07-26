package io.github.silencelwy.generator.file.service;

import io.github.silencelwy.generator.file.dto.*;


public interface  ITemplateCodeService<T extends ClassCodeDto> {
    String METHODS = "CRUDB";

    /**
     * 文件生成接口
     * @author mb.wang
     * @date 2018/5/21 09:05
     * @param t
     * @return void
     */
    void generatedFile(T t);

}
