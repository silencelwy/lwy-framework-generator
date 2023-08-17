package io.github.silencelwy.generator.mongo;

import lombok.Data;

import java.util.List;


@Data
public class ClassField {
    private String name;
    private String type;
    private List<ClassField> subFields;
    private Boolean array;

    public ClassField(String name, String type) {
        this.name = name;
        this.type = type;
    }


}