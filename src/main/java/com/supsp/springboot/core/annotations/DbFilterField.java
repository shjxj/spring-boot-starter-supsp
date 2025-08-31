package com.supsp.springboot.core.annotations;


import com.supsp.springboot.core.enums.QueryType;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DbFilterField {
    String property() default "";

    String column() default "";

    QueryType type() default QueryType.in;

    boolean sensitive() default false;
}
