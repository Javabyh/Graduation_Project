package com.aiit.byh.service.common.db;

import org.mybatis.spring.SqlSessionTemplate;

/**
 * Created by Bing_Yinghan on 2019/10/20 17:03
 */
public abstract class ReadBaseDao2 extends BaseDao{

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate){
        super.setReadonly(true);
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }
}
