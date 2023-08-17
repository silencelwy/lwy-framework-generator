package io.github.silencelwy.generator.table.service.impl;

import io.github.silencelwy.generator.table.dto.AssociativeTableDto;
import io.github.silencelwy.generator.table.dto.TableDto;
import io.github.silencelwy.generator.table.service.ITableService;

import java.sql.SQLException;

public class BeanVOService implements ITableService {
    @Override
    public TableDto getColumnsByTable(String table, String schema) throws SQLException {
//        UpdateHelper.getCollectionPrimaryName()
        return null;
    }

    @Override
    public TableDto getTablesInfo(String table, String schema) throws SQLException {
        return null;
    }

    @Override
    public AssociativeTableDto parseAssociativeTable(String table, String schema) throws SQLException {
        return null;
    }
}
