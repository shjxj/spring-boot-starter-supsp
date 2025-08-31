package com.supsp.springboot.core.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StateEvent {
    Class<?> stateClass() default void.class;
}
