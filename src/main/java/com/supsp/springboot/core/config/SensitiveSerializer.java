package com.supsp.springboot.core.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.supsp.springboot.core.annotations.SensitiveField;
import com.supsp.springboot.core.enums.SensitiveType;
import com.supsp.springboot.core.threads.SensitiveContext;
import com.supsp.springboot.core.vo.SensitiveAll;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class SensitiveSerializer<T> extends JsonSerializer<T> implements ContextualSerializer {

    private SensitiveField sensitive;

    public SensitiveSerializer() {
    }

    public SensitiveSerializer(SensitiveField sensitive) {
        this.sensitive = sensitive;
    }

    @Override
    public void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.isNull(t)) {
            jsonGenerator.writeObject(null);
            return;
        }

        if (
                ObjectUtils.isNotEmpty(sensitive)
                        && ObjectUtils.isNotEmpty(sensitive.type())
                        && !sensitive.type().equals(SensitiveType.NONE)
        ) {
            Boolean allSensitive = SensitiveContext.get(SensitiveAll.class);
            if (allSensitive != null && allSensitive.equals(Boolean.FALSE)) {
                jsonGenerator.writeObject(t);
                return;
            }

            try {
                Class<?> valueClass = jsonGenerator.getOutputContext().getCurrentValue().getClass();
                Boolean doSensitive = SensitiveContext.get(valueClass);
                if (doSensitive != null && doSensitive.equals(Boolean.FALSE)) {
                    jsonGenerator.writeObject(t);
                    return;
                }
            } catch (Exception e) {
                //
            }

            jsonGenerator.writeString(sensitive.type().maskData(String.valueOf(t)));
            return;
        }
        jsonGenerator.writeObject(t);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        // 为空直接跳过
        if (beanProperty == null) {
            return serializerProvider.findNullValueSerializer(null);
        }

        // 非String类直接跳过
        if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
            SensitiveField sensitiveField = beanProperty.getAnnotation(SensitiveField.class);
            if (sensitiveField == null) {
                sensitiveField = beanProperty.getContextAnnotation(SensitiveField.class);
            }

            if (sensitiveField != null) {
                return new SensitiveSerializer<>(sensitiveField);
            }
        }

        return serializerProvider.findValueSerializer(
                beanProperty.getType(),
                beanProperty
        );
    }


}
