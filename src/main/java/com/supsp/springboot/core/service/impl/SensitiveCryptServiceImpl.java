package com.supsp.springboot.core.service.impl;

import com.supsp.springboot.core.annotations.SensitiveData;
import com.supsp.springboot.core.annotations.SensitiveField;
import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.exceptions.ExceptionCodes;
import com.supsp.springboot.core.exceptions.ModelException;
import com.supsp.springboot.core.service.ISensitiveCryptService;
import com.supsp.springboot.core.utils.AESUtil;
import com.supsp.springboot.core.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@Slf4j
public class SensitiveCryptServiceImpl implements ISensitiveCryptService {

    @Override
    public <T> T encrypt(T object) throws ModelException {
        if (
                ObjectUtils.isEmpty(object)
                        || !object.getClass().isAnnotationPresent(SensitiveData.class)
        ) {
            return object;
        }

        Field[] fields = object.getClass().getDeclaredFields();
        if (ArrayUtils.isEmpty(fields)) {
            return object;
        }

        try {
            for (Field field : fields) {
                SensitiveField sensitive = AnnotationUtils.findAnnotation(field, SensitiveField.class);
                if (
                        ObjectUtils.isEmpty(sensitive)
                                || !sensitive.db()
                ) {
                    continue;
                }

                field.setAccessible(true);
                Object value = field.get(object);
                if (ObjectUtils.isEmpty(value)) {
                    continue;
                }

                String val = String.valueOf(value);
                if (StrUtils.isBlank(val)) {
                    continue;
                }

                field.set(
                        object,
                        encryptString(val)
                );

            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new ModelException(ExceptionCodes.OPERATION_FAILURE);
        }
        return object;
    }

    @Override
    public <T> T decrypt(T object) throws ModelException {
        if (
                ObjectUtils.isEmpty(object)
                        || !object.getClass().isAnnotationPresent(SensitiveData.class)
        ) {
            return object;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        if (ArrayUtils.isEmpty(fields)) {
            return object;
        }

        try {
            for (Field field : fields) {
                SensitiveField sensitive = AnnotationUtils.findAnnotation(field, SensitiveField.class);
                if (
                        ObjectUtils.isEmpty(sensitive)
                                || !sensitive.db()
                ) {
                    continue;
                }

                field.setAccessible(true);
                Object value = field.get(object);
                if (
                        value == null
                                || ObjectUtils.isEmpty(object)) {
                    continue;
                }

                String val = String.valueOf(value);
                if (StrUtils.isBlank(val)) {
                    continue;
                }

                field.set(
                        object,
                        decryptString(val)
                );
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new ModelException(ExceptionCodes.OPERATION_FAILURE);
        }

        return object;
    }


    @Override
    public String encryptKey() {
        return AESUtil.encodeKey(CoreProperties.ENCODE_KEY);
    }

    @Override
    public String encryptString(String data) {
        if (StrUtils.isEmpty(data)) {
            return data;
        }
        String res = AESUtil.encrypt(data);
        if (StrUtils.isBlank(res)) {
            return data;
        }
        return res;
    }

    @Override
    public String decryptString(String data) {
        if (StrUtils.isEmpty(data)) {
            return data;
        }
        String res = AESUtil.decrypt(data);
        if (StrUtils.isBlank(res)) {
            return data;
        }
        return res;
    }
}
