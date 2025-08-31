package com.supsp.springboot.core.annotations;

import java.lang.annotation.*;

/**
 * 标识实体相关属性
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DBEntity {
}
