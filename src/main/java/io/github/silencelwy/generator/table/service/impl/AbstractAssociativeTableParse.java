package io.github.silencelwy.generator.table.service.impl;

import io.github.silencelwy.generator.table.dto.AssociativeTableDto;
import io.github.silencelwy.generator.table.dto.TableRelationalMappingType;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * @author mb.wang
 * @version V1.0
 * @Package io.github.silencelwy.generator.table.service
 * @Description: 关联表解析
 * @date 2018/5/22 15:39
 */
@Slf4j
public abstract class AbstractAssociativeTableParse   {
    public static final String ONE_TO_ONE = "--";
    public static final String ONE_TO_MORE = "->";
    public static final String TABLE_SPLIT_COMMA = ",";

    /**
     * 解析主表信息
     *
     * @param table
     * @param schema
     * @return io.github.silencelwy.generator.table.dto.TableDto
     * @throws
     * @author mb.wang
     * @date 2018/5/22 15:51
     */
//    public TableDto parseMainTable(final String table,final String schema) throws SQLException {
//        return getColumnsByTable(parseMainTableName(table), schema);
//    }

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
