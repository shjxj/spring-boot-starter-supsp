package com.supsp.springboot.core.annotations;


import com.supsp.springboot.core.enums.IgnoreValue;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ApiIdempotent {
    IgnoreValue ignore() default IgnoreValue.False;
     long expires() default 0;
}
