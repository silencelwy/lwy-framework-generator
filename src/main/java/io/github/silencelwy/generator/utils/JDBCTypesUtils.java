package io.github.silencelwy.generator.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Types;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * java.sql.Types数据类型与java数据类型转换
 * @author mb.wang
 * @date 2018/5/18 15:41
 */
public class JDBCTypesUtils {
    /**
     * @Fields field : Name to value
     * @author mb.wang
     * @date 2018/5/18 15:42
     */
    private static Map<String, Integer> jdbcTypes;
    /**
     * @Fields field : value to Name
     * @author mb.wang
     * @date 2018/5/18 15:42
     */
    private static Map<Integer, String> jdbcTypeValues;
    /**
     * @Fields field : jdbc type to java type
     * @author mb.wang
     * @date 2018/5/18 15:42
     */
    private static Map<Integer, Class<?>> jdbcJavaTypes;

    static {
        jdbcTypes = new TreeMap<>();
        jdbcTypeValues = new TreeMap<>();
        jdbcJavaTypes = new TreeMap<>();
        Field[] fields = Types.class.getFields();
        for (int i = 0, len = fields.length; i < len; ++i) {
            if (Modifier.isStatic(fields[i].getModifiers())) {
                try {
                    String name = fields[i].getName();
                    Integer value = (Integer) fields[i].get(Types.class);
                    jdbcTypes.put(name, value);
                    jdbcTypeValues.put(value, name);
                } catch (IllegalArgumentException e) {
                } catch (IllegalAccessException e) {
                }
            }
        }
        // 初始化jdbcJavaTypes：
        jdbcJavaTypes.put(new Integer(Types.LONGNVARCHAR), String.class);
        jdbcJavaTypes.put(new Integer(Types.NCHAR), String.class);
        jdbcJavaTypes.put(new Integer(Types.NVARCHAR), String.class);
        jdbcJavaTypes.put(new Integer(Types.ROWID), String.class);
        jdbcJavaTypes.put(new Integer(Types.BIT), Boolean.class);
        jdbcJavaTypes.put(new Integer(Types.TINYINT), Byte.class);
        jdbcJavaTypes.put(new Integer(Types.BIGINT), Long.class);
        jdbcJavaTypes.put(new Integer(Types.LONGVARBINARY), Blob.class);
        jdbcJavaTypes.put(new Integer(Types.VARBINARY), Blob.class);
        jdbcJavaTypes.put(new Integer(Types.BINARY), Blob.class);
        jdbcJavaTypes.put(new Integer(Types.LONGVARCHAR), String.class);
//  jdbcJavaTypes.put(new Integer(Types.NULL), String.class);
        jdbcJavaTypes.put(new Integer(Types.CHAR), String.class);
        jdbcJavaTypes.put(new Integer(Types.NUMERIC), BigDecimal.class);
        jdbcJavaTypes.put(new Integer(Types.DECIMAL), BigDecimal.class);
        jdbcJavaTypes.put(new Integer(Types.INTEGER), Integer.class);
        jdbcJavaTypes.put(new Integer(Types.SMALLINT), Short.class);
        jdbcJavaTypes.put(new Integer(Types.FLOAT), BigDecimal.class);
        jdbcJavaTypes.put(new Integer(Types.REAL), BigDecimal.class);
        jdbcJavaTypes.put(new Integer(Types.DOUBLE), BigDecimal.class);
        jdbcJavaTypes.put(new Integer(Types.VARCHAR), String.class);
        jdbcJavaTypes.put(new Integer(Types.BOOLEAN), Boolean.class);
//  jdbcJavaTypes.put(new Integer(Types.DATALINK), String.class);   // 70 /
        jdbcJavaTypes.put(new Integer(Types.DATE), Date.class);
        jdbcJavaTypes.put(new Integer(Types.TIME), Date.class);
        jdbcJavaTypes.put(new Integer(Types.TIMESTAMP), Date.class);
        jdbcJavaTypes.put(new Integer(Types.OTHER), Object.class);
//  jdbcJavaTypes.put(new Integer(Types.JAVA_OBJECT), Object.class);
//  jdbcJavaTypes.put(new Integer(Types.DISTINCT), String.class);
//  jdbcJavaTypes.put(new Integer(Types.STRUCT), String.class);
//  jdbcJavaTypes.put(new Integer(Types.ARRAY), String.class);
        jdbcJavaTypes.put(new Integer(Types.BLOB), Blob.class);
        jdbcJavaTypes.put(new Integer(Types.CLOB), Clob.class);
//  jdbcJavaTypes.put(new Integer(Types.REF), String.class);
//  jdbcJavaTypes.put(new Integer(Types.SQLXML), String.class);
        jdbcJavaTypes.put(new Integer(Types.NCLOB), Clob.class);
    }

    /**
     * 获取jdbc类型code
     * @author mb.wang
     * @date 2018/5/18 15:44
     * @param jdbcName
     * @return int
     */
    public static int getJdbcCode(String jdbcName) {
        return jdbcTypes.get(jdbcName);
    }
    /**
     * 获取
     * @author mb.wang
     * @date 2018/5/18 15:44
     * @param jdbcCode
     * @return String
     */
    public static String getJdbcName(int jdbcCode) {
        return jdbcTypeValues.get(jdbcCode);
    }
    /**
     * 获取jdbc类型code
     * @author mb.wang
     * @date 2018/5/18 15:44
     * @param jdbcName
     * @return int
     */
    public static Class<?> jdbcTypeToJavaType(int jdbcType) {
        return jdbcJavaTypes.get(jdbcType);
    }
    /**
     * 获取jdbc类型code
     * @author mb.wang
     * @date 2018/5/18 15:44
     * @param jdbcName
     * @return int
     */
    public static boolean isJavaNumberType(int jdbcType) {
        Class<?> type = jdbcJavaTypes.get(jdbcType);
        return (type == null) ? false : (Number.class.isAssignableFrom(type)) ? true : false;
    }

}