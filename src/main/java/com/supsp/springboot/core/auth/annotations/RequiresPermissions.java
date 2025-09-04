package com.supsp.springboot.core.auth.annotations;

import com.supsp.springboot.core.enums.Logical;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermissions {
    // 权限标识数组
    String[] value();

    // 逻辑关系
    Logical logical() default Logical.AND;
}