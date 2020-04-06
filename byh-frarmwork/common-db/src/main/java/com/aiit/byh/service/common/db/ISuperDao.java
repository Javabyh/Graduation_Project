package com.aiit.byh.service.common.db;

import java.util.List;
import java.util.Map;

/**
 * Created by Bing_Yinghan on 2019/10/20 17:03
 */
public interface ISuperDao {

    public Integer update(String statementName, Object parameterObject);

    public Integer delete(String statementName, Object parmaeterObject);

    public <T> T getObject(String statementName, Object parameterObject);

    public <T> List<T> getList(String statementName, Object parameterObject);

    public <T, V> Map<T, V> getMap(String statementName, Object parameterObject);
}
