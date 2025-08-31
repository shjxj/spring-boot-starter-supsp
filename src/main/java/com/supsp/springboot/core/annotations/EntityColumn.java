package com.supsp.springboot.core.annotations;


import com.supsp.springboot.core.enums.DictScene;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityColumn {
    boolean isDataId() default false;

    boolean isTitle() default false;

    boolean isCode() default false;

    boolean isKeywords() default false;

    boolean isType() default false;

    boolean isImage() default false;

    boolean isFile() default false;

    boolean multipleFile() default false;

    boolean isDict() default false;

    DictScene scene() default DictScene.none;

    String dictCode() default "";

    boolean select() default true;

    boolean defaultSelect() default true;

    boolean apiSelect() default true;

    boolean defaultApiSelect() default true;
}
