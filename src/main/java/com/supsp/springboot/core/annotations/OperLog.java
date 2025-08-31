package com.supsp.springboot.core.annotations;


import com.supsp.springboot.core.enums.IgnoreValue;
import com.supsp.springboot.core.enums.LogOperation;
import com.supsp.springboot.core.enums.SysApp;
import com.supsp.springboot.core.enums.SysModule;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {

    /**
     * 日志模块
     *
     * @return
     */
    SysModule module() default SysModule.normal;

    String moduleName() default "";

    /**
     * 日志应用
     *
     * @return
     */
    SysApp app() default SysApp.normal;
    String appName() default "";


    /**
     * 控制器名称
     *
     * @return
     */
    String controller() default "";

    /**
     * 方法名称
     *
     * @return
     */
    String method() default "";

    /**
     * 日志操作
     *
     * @return
     */
    LogOperation operation() default LogOperation.none;
    String operationName() default "";

    /**
     * 日志操作 子项
     *
     * @return
     */
    LogOperation childOperation() default LogOperation.none;
    String childOperationName() default "";

    /**
     * 忽略日志
     *
     * @return
     */
    IgnoreValue ignore() default IgnoreValue.Null;
}
