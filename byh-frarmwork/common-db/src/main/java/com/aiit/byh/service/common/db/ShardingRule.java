package com.aiit.byh.service.common.db;

import org.springframework.util.Assert;

/**
 *
 */
public class ShardingRule {

    /**
     *
     * @param splitIndexkey
     * @param readonly
     * @return
     */
    public static String getDataSourceKey(int splitIndexkey,boolean readonly){
        Assert.notNull(splitIndexkey, "splitIndexkey  must not be null");
        String dataSourceKey = "";
        if (splitIndexkey == 1) {	// mysql
            if (readonly) {
                dataSourceKey = DataSourceContextHolder.DB_SHARDING_READ_1;
            } else {
                dataSourceKey = DataSourceContextHolder.DB_SHARDING_WRITE_1;
            }
        } else if (splitIndexkey == 2) {	// sqlserver db1
            if (readonly) {
                dataSourceKey = DataSourceContextHolder.DB_SHARDING_READ_2;
            } else {
                dataSourceKey = DataSourceContextHolder.DB_SHARDING_WRITE_2;
            }

        }  else if (splitIndexkey == 3) {	// sqlserver db2
            if (readonly) {
                dataSourceKey = DataSourceContextHolder.DB_SHARDING_READ_3;
            } else {
                dataSourceKey = DataSourceContextHolder.DB_SHARDING_WRITE_3;
            }

        } else if (splitIndexkey == 4) {	// sqlserver db2
            if (readonly) {
                dataSourceKey = DataSourceContextHolder.DB_SHARDING_READ_4;
            } else {
                dataSourceKey = DataSourceContextHolder.DB_SHARDING_WRITE_4;
            }

        } else if (splitIndexkey == 5) {	// sqlserver db2
            if (readonly) {
                dataSourceKey = DataSourceContextHolder.DB_SHARDING_READ_5;
            } else {
                dataSourceKey = DataSourceContextHolder.DB_SHARDING_WRITE_5;
            }

        } else {
            if (readonly) {
                dataSourceKey = DataSourceContextHolder.DB_SHARDING_READ_2;
            } else {
                dataSourceKey = DataSourceContextHolder.DB_SHARDING_WRITE_2;
            }
        }
        return dataSourceKey;
    }
}
