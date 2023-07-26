package io.github.silencelwy.generator.table.service.impl;

import io.github.silencelwy.generator.table.dto.AssociativeTableDto;
import io.github.silencelwy.generator.table.dto.ColumnDto;
import io.github.silencelwy.generator.table.dto.TableDto;
import io.github.silencelwy.generator.table.dto.TableRelationalMappingType;
import io.github.silencelwy.generator.table.service.ITableService;
import io.github.silencelwy.generator.utils.DruidUtils;
import io.github.silencelwy.generator.utils.JDBCTypesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author mb.wang
 * @version V1.0
 * @Package io.github.silencelwy.generator.table.service
 * @Description: 数据库表相关操作
 * @date 2018/5/18 17:01
 */
@Slf4j
public class TableService extends AbstractAssociativeTableParse implements ITableService {

    /**
     * 获取数据库中所有的表名称
     *
     * @param conn 数据库的连接
     * @return 该数据库中所有的表名称
     * @throws SQLException
     */
    private List<String> getTables(Connection conn) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet resultSet = metaData.getTables(conn.getCatalog(), "%", null, new String[]{"TABLE"});
        List<String> tables = new ArrayList<>();
        while (resultSet.next()) {
            String tableName = resultSet.getString("TABLE_NAME");
            tables.add(tableName);
        }
        return tables;
    }

    /**
     * 获取指定表的所有字段名称
     *
     * @param conn      数据库连接
     * @param tableName 表名称
     * @return 该表所有的字段名称
     * @throws SQLException
     */
    private List<String> getColumns(Connection conn, String tableName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getColumns(conn.getCatalog(), null, tableName, null);
        List<String> columns = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("COLUMN_NAME");
            columns.add(name);
        }
        return columns;
    }

    protected ColumnDto getPrimaryKey(String table, String schema) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ColumnDto columnDto = new ColumnDto();
        ResultSet rs = null;

        try {
            rs = metaData.getPrimaryKeys(connection.getCatalog(), null, table);
            TreeMap<Short, String> keyColumns = new TreeMap<>();
            while (rs.next()) {
                columnDto.setJdbcName(rs.getString("COLUMN_NAME"));
                short keySeq = rs.getShort("KEY_SEQ");
                keyColumns.put(keySeq, columnDto.getName());
            }
            for (String columnName : keyColumns.values()) {
                log.info("主键：{}",columnName);
            }
        } catch (SQLException e) {
            log.error("获取{}库中的{}表主键失败。",schema,table);
            throw e;
        } finally {
            DruidUtils.close(rs, connection);
        }

        return columnDto;
    }

    /**
     * 获取表对应的列信息
     * @author mb.wang
     * @date 2018/5/19 18:48
     * @param   table
     * @param   schema
     * @return  TableDto
     * @throws SQLException
     */
    @Override
    public TableDto getColumnsByTable(String table, String schema) throws SQLException {
        String tableTmp = parseMainTableName(table);

        Connection connection = DruidUtils.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();

        List<String> tables = getTables(connection);

        TableDto tableDto = getTablesInfo(tableTmp, schema);
        ColumnDto primaryKeyColumn = getPrimaryKey(tableTmp,schema);
        List<ColumnDto> columnDtoList = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = metaData.getColumns(connection.getCatalog(), null, tableTmp, null);
            int jdbcType = 0;
            int order = 0;
            while (rs.next()) {
                order++;

                ColumnDto columnDto = new ColumnDto();

                jdbcType = rs.getInt("DATA_TYPE");
                log.debug("jdbc类型:{}", JDBCTypesUtils.getJdbcName(jdbcType));
                columnDto.setJdbcType(JDBCTypesUtils.getJdbcName(jdbcType));
                columnDto.setJavaType(JDBCTypesUtils.jdbcTypeToJavaType(jdbcType).getSimpleName());
                log.debug("java类型:{}",columnDto.getJavaType());

                columnDto.setLength(rs.getInt("COLUMN_SIZE"));
                columnDto.setJdbcName(rs.getString("COLUMN_NAME"));
                columnDto.setNullable(rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable);
                columnDto.setRemarks(rs.getString("REMARKS"));
                columnDto.setDefaultValue(rs.getString("COLUMN_DEF"));
                columnDto.setOrder(columnDto.getDefaultValue()==null?0:order);
                columnDto.setPrimaryKey(primaryKeyColumn==null?false: StringUtils.equals(primaryKeyColumn.getName(),columnDto.getName()));

                if(columnDto.isPrimaryKey()){
                    tableDto.setPrimaryKey(columnDto);
                }
                columnDtoList.add(columnDto);
            }

        } catch (Exception ex) {
            log.error("获取{}库中的{}表列信息失败。",schema,table);
            throw ex;
        } finally {
            DruidUtils.close(rs, connection);
        }

        tableDto.setColumns(columnDtoList);

        return tableDto;
    }

    @Override
    public TableDto getTablesInfo(String table, String schema) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        TableDto tableDto = new TableDto(table);

        ResultSet rs = null;
        try {
            /**
             * 获取给定类别中使用的表的描述。
             * 方法原型:ResultSet getTables(String catalog,String schemaPattern,String tableNamePattern,String[] types);
             * catalog - 表所在的类别名称;""表示获取没有类别的列,null表示获取所有类别的列。
             * schema - 表所在的模式名称(oracle中对应于Tablespace);""表示获取没有模式的列,null标识获取所有模式的列; 可包含单字符通配符("_"),或多字符通配符("%");
             * tableNamePattern - 表名称;可包含单字符通配符("_"),或多字符通配符("%");
             * types - 表类型数组; "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS" 和 "SYNONYM";null表示包含所有的表类型;可包含单字符通配符("_"),或多字符通配符("%");
             */
            rs = databaseMetaData.getTables(null, schema, table, new String[]{"TABLE", "VIEW"});


            while (rs.next()) {
                tableDto.setName(rs.getString("TABLE_NAME"));
                tableDto.setType(rs.getString("TABLE_TYPE"));
                tableDto.setRemarks(rs.getString("REMARKS"));

            }
        } catch (Exception ex) {
            log.error("获取{}库中的{}表信息失败。",schema,table);
            throw ex;
        } finally {
            DruidUtils.close(rs, connection);
        }

        return tableDto;
    }

    @Override
    public AssociativeTableDto parseAssociativeTable(String table, String schema) throws SQLException {
        AssociativeTableDto associativeTableDto =  new AssociativeTableDto();
        List<String> associativeTables = parseAssociativeTableName(table,associativeTableDto);

        if(associativeTables!=null&&associativeTables.size()>0){
            List<TableDto> tableDtos = new ArrayList<>();

            for(String ass:associativeTables){
                tableDtos.add(getColumnsByTable(ass,schema));

                if(StringUtils.equals(associativeTableDto.getRelationalMappingType().getType(),TableRelationalMappingType.ONE_TO_ONE.getType())){
                    //如果是1-1，则解析完第一个后，跳出循环
                    break;
                }
            }
            associativeTableDto.setAssociativeTables(tableDtos);
        }

        return associativeTableDto;
    }

}
