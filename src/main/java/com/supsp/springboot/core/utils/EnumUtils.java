package com.supsp.springboot.core.utils;


import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EnumUtils {

    private final static List<String> ENUM_VALUE_KEY = Arrays.asList(
            "getCode",
            "getDesc"
    );

    @SuppressWarnings("unchecked")
    public static Map<String, Map<String, String>> getEnumValues(Class<?> clz) {
        if (clz == null || !clz.isEnum()) {
            return null;
        }
        Class<Enum> enumClass = (Class<Enum>) clz;
        Method[] methods = enumClass.getMethods();
        if (ArrayUtils.isEmpty(methods)) {
            return null;
        }
        Map<String, Map<String, String>> res = new HashMap<>();
        for (Enum object : enumClass.getEnumConstants()) {
            String key = object.toString();
            Map<String, String> values = new HashMap<>();
            for (Method method : methods) {
                if (
                        !method.getName().startsWith("get")
                                || !ENUM_VALUE_KEY.contains(method.getName())
                ) {
                    continue;
                }
                String name = CaseFormat.UPPER_CAMEL.to(
                        CaseFormat.LOWER_CAMEL,
                        StrUtils.substring(method.getName(), 3)
                );
                try {
                    Object val = method.invoke(object);
                    values.put(name, val.toString());
                } catch (Exception e) {
                    continue;
                }
            }
            res.put(
                    key,
                    values
            );
        }
        return res;
    }

    public static List<Map<String, String>> docEnumValues(Class<?> clz) {
        Map<String, Map<String, String>> values = getEnumValues(clz);
        if (CommonUtils.isEmpty(values)) {
            return null;
        }
        return values.values().stream()
                .filter(v -> v.containsKey("code") && v.containsKey("desc"))
                .toList();
    }

    public static Map<String, String> docEnumExtValues(Class<?> clz) {
        List<Map<String, String>> values = docEnumValues(clz);
        if (CommonUtils.isEmpty(values)) {
            return null;
        }

        assert values != null;
        return values.stream()
                .filter(v -> v.containsKey("code") && v.containsKey("desc"))
                .collect(
                        Collectors.toMap(
                                v -> v.get("code"),
                                e -> e.get("desc"),
                                (oldVal, newVal) -> newVal
                        )
                );
    }
}
