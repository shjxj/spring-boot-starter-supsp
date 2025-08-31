package com.supsp.springboot.core.annotations;

import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.enums.BooleanValue;
import com.supsp.springboot.core.enums.CacheType;
import com.supsp.springboot.core.enums.DataScope;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cacher {
    String name() default "";

    String key() default "";

    long timeOut() default 0;

    TimeUnit timeUnit() default TimeUnit.MINUTES;

    CacheType type() default CacheType.FULL;

    boolean sync() default false;

    DataScope scope() default DataScope.Null;

    BooleanValue auth() default BooleanValue.Null;

    AuthMemberType memberType() default AuthMemberType.none;

    BooleanValue memberId() default BooleanValue.Null;

    BooleanValue orgId() default BooleanValue.Null;

    BooleanValue storeId() default BooleanValue.Null;

    BooleanValue shopId() default BooleanValue.Null;
}
