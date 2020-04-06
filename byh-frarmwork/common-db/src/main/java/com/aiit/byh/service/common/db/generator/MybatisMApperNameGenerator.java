package com.aiit.byh.service.common.db.generator;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

import java.util.UUID;

/**
 * Created by Bing_Yinghan on 2019/10/20 17:04
 */
public class MybatisMApperNameGenerator implements BeanNameGenerator {

    public String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry beanDefinitionRegistry) {
        return beanDefinition.getBeanClassName() + UUID.randomUUID().toString();
    }
}
