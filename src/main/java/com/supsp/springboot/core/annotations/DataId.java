package com.supsp.springboot.core.annotations;

import java.lang.annotation.*;

/**
 * 标识数据库 ID字段 非数据库自增ID
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataId {
    String value() default "";
}
