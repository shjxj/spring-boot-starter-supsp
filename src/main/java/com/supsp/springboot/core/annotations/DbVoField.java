package com.supsp.springboot.core.annotations;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DbVoField {
    String property() default "";

    String column() default "";

    boolean exists() default false;
}
