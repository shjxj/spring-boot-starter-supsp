package com.supsp.springboot.core.annotations;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveData {
}
