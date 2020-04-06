package com.aiit.byh.service.common.db;

public final class DataSourceContextHolder {

    public static final String DB_SHARDING_READ_1 = "db_sharding_read_1";
    public static final String DB_SHARDING_READ_2 = "db_sharding_read_2";
    public static final String DB_SHARDING_READ_3 = "db_sharding_read_3";
    public static final String DB_SHARDING_READ_4 = "db_sharding_read_4";
    public static final String DB_SHARDING_READ_5 = "db_sharding_read_5";

    public static final String DB_SHARDING_WRITE_1 = "db_sharding_write_1";
    public static final String DB_SHARDING_WRITE_2 = "db_sharding_write_2";
    public static final String DB_SHARDING_WRITE_3 = "db_sharding_write_3";
    public static final String DB_SHARDING_WRITE_4 = "db_sharding_write_4";
    public static final String DB_SHARDING_WRITE_5 = "db_sharding_write_5";

    /**
     * 局部线程变量，线程安全
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return (String) contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
