package com.aiit.byh.service.common.db;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by Bing_Yinghan on 2019/10/20 17:00
 */
public abstract class ReadBaseDao extends BaseDao {

    @Autowired
    @Qualifier("sqlsessionRead")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setReadonly(true);
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }
}
