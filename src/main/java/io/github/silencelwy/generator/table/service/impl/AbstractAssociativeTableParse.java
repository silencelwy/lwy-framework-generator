package io.github.silencelwy.generator.table.service.impl;

import io.github.silencelwy.generator.table.dto.AssociativeTableDto;
import io.github.silencelwy.generator.table.dto.TableRelationalMappingType;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public abstract class AbstractAssociativeTableParse   {
    public static final String ONE_TO_ONE = "--";
    public static final String ONE_TO_MORE = "->";
    public static final String TABLE_SPLIT_COMMA = ",";

    protected String parseMainTableName(String table) {
        String[] str = table.split("-");
        return str.length > 0 ? str[0]:table ;
    }

    protected List<String> parseAssociativeTableName(String table, AssociativeTableDto associativeTableDto) {
        String associativeTableNameTemp = null;

        if (table.contains(ONE_TO_MORE)) {
            associativeTableNameTemp = table.split(ONE_TO_MORE)[1];
            associativeTableDto.setRelationalMappingType(TableRelationalMappingType.ONE_TO_MORE);
            log.debug("{} 一对多", table);
        } else if (table.contains(ONE_TO_ONE)) {
            associativeTableNameTemp = table.split(ONE_TO_ONE)[1];
            associativeTableDto.setRelationalMappingType(TableRelationalMappingType.ONE_TO_ONE);
            log.debug("{} 一对一", table);
        } else {
            associativeTableDto.setRelationalMappingType(TableRelationalMappingType.NONE);
            log.debug("{} 只配置了主表。", table);
        }

        return associativeTableNameTemp != null ? Arrays.asList(associativeTableNameTemp.split(TABLE_SPLIT_COMMA)) : null;
    }
}
