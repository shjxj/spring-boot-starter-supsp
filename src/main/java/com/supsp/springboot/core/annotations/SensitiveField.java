package com.supsp.springboot.core.annotations;



import com.supsp.springboot.core.enums.SensitiveType;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveField {

    /**
     * 显示方式
     *
     * @return
     */
    SensitiveType type() default SensitiveType.DEFAULT;

    /**
     * 入库是否加密
     *
     * @return
     */
    boolean db() default true;
}
