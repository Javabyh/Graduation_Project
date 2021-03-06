package com.aiit.byh.service.common.db;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by Bing_Yinghan on 2019/10/20 17:03
 */
public abstract class WriteBaseDao extends BaseDao {

    @Autowired
    @Qualifier("sqlsessionWrite")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setReadonly(false);
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }
}
