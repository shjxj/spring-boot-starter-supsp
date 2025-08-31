package com.supsp.springboot.core.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

@Slf4j
public class UniqueNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    public String generateBeanName(
            BeanDefinition definition,
            BeanDefinitionRegistry registry
    ) {

        String shortClassName = super.generateBeanName(definition, registry);

        // 如果存在相同名称的bean，则将要注册的bean的名称设置为全路径名
        if (registry.containsBeanDefinition(shortClassName)) {
            String beanClassName = definition.getBeanClassName();
            String[] arr = StringUtils.split(beanClassName, ".");
            String name = beanClassName;
            String[] newArr = new String[0];
            if (arr != null) {
                if (arr.length > 2) {
                    newArr = ArrayUtils.subarray(arr, arr.length - 2, arr.length);
                } else {
                    newArr = arr;
                }
            }
            if (newArr != null && newArr.length > 0) {
                name = StringUtils.join(newArr, "");
            }

            if (registry.containsBeanDefinition(name)) {
                return beanClassName;
            }
            return name;
        }

        return shortClassName;
    }
}
