package com.supsp.springboot.core.annotations;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sensitive {
    Class<?>[] disable() default {};
    Class<?>[] enable() default {};
}
