package io.github.silencelwy.generator.table.service;

import io.github.silencelwy.generator.table.dto.AssociativeTableDto;
import io.github.silencelwy.generator.table.dto.TableDto;

import java.sql.SQLException;

public interface ITableService {
    TableDto getColumnsByTable(String table, String schema) throws SQLException;
    TableDto getTablesInfo(String table, String schema) throws SQLException;
    AssociativeTableDto parseAssociativeTable(final String table, final String schema) throws SQLException;
}
