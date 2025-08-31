package com.supsp.springboot.core.annotations;



import com.supsp.springboot.core.enums.DataScope;

import java.lang.annotation.*;

/**
 * db model 相关定义
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DbModel {

    /**
     * 是否需要权限
     *
     * @return
     */
    DataScope scope() default DataScope.Null;

    /**
     * 是否支持标签
     *
     * @return
     */
    boolean tag() default false;
}
