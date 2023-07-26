package io.github.silencelwy.generator.table.service;

import io.github.silencelwy.generator.table.dto.AssociativeTableDto;
import io.github.silencelwy.generator.table.dto.TableDto;

import java.sql.SQLException;

/**
 * @author mb.wang
 * @version V1.0
 * @Package io.github.silencelwy.generator.table.service
 * @Description: 数据解析接口定义
 * @date 2018/7/4 16:23
 */

public interface ITableService {
    TableDto getColumnsByTable(String table, String schema) throws SQLException;
    TableDto getTablesInfo(String table, String schema) throws SQLException;

    /**
     * 解析从表信息
     *
     * @param table
     * @param schema
     * @return io.github.silencelwy.generator.table.dto.TableDto
     * @throws
     * @author mb.wang
     * @date 2018/5/22 15:51
     */
    AssociativeTableDto parseAssociativeTable(final String table, final String schema) throws SQLException;
}
