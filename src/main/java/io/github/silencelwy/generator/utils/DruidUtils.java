package io.github.silencelwy.generator.utils;

import io.github.silencelwy.generator.project.config.ProjectConfig;
import io.github.silencelwy.generator.project.dto.ProjectDto;
import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DruidUtils {
    private DruidUtils() {
    }
    static DruidDataSource dataSource = null;

    static {
        ProjectDto projectDto = ProjectConfig.getProjectConfig().getProjectDto();

        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(projectDto.getJdbcDiverClassName());
        dataSource.setUrl(projectDto.getJdbcUrl());
        dataSource.setUsername(projectDto.getJdbcUsername());
        dataSource.setPassword(projectDto.getJdbcPassword());
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close(ResultSet rs, Connection conn) {
        try {
            if (rs != null) {

                rs.close();

            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
