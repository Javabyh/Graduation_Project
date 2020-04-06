package com.aiit.byh.service.common.db;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public abstract class BaseDao extends SqlSessionDaoSupport {
    private boolean readonly;

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public SqlSession getSqlSession() {
        DataSourceContextHolder.clear();
        return super.getSqlSession();
    }

    public SqlSession getSqlSession(int splitIndexkey) {
        String key = ShardingRule.getDataSourceKey(splitIndexkey, readonly);
        DataSourceContextHolder.setDataSourceType(key);
        return super.getSqlSession();
    }

    public SqlSession getSqlSession(String dataSourceKey) {
        DataSourceContextHolder.setDataSourceType(dataSourceKey);
        return super.getSqlSession();
    }
}
