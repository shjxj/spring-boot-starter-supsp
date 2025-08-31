package com.supsp.springboot.core.config;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import com.supsp.springboot.core.annotations.SensitiveField;

import java.io.Serial;

public class SensitiveFieldAnnotationIntrospector extends NopAnnotationIntrospector {
    @Serial
    private static final long serialVersionUID = 5529081037923077035L;

    @Override
    public Object findSerializer(Annotated am) {
        SensitiveField annotation = am.getAnnotation(SensitiveField.class);
        if (annotation !=null){
            return SensitiveSerializer.class;
        }
        return super.findSerializer(am);
    }
}
