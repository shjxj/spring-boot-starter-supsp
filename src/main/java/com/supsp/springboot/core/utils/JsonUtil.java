package com.supsp.springboot.core.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.supsp.springboot.core.config.BigNumberSerializer;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class JsonUtil {

    public static final Jackson2ObjectMapperBuilderCustomizer CUSTOMIZER = jackson2ObjectMapperBuilderCustomizer();

    /**
     * 构建 Jackson 自定义配置
     *
     * @return
     */
    public static Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            // 这部分也可以在 yaml 中配置
            builder
                    // 序列化时，对象为 null，是否抛异常
                    .failOnEmptyBeans(false)
                    // 反序列化时，json 中包含 pojo 不存在属性时，是否抛异常
                    .failOnUnknownProperties(false)
                    // 禁止将 java.util.Date, Calendar 序列化为数字(时间戳)
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    // 设置 java.util.Date, Calendar 序列化、反序列化的格式
                    .dateFormat(new SimpleDateFormat(CommonTools.DATE_TIME_PATTERN))
                    // 设置 java.util.Date、Calendar 序列化、反序列化的时区
                    .timeZone(TimeZone.getTimeZone("GMT+8"))
            ;

            // 配置 Jackson 序列化 BigDecimal 时使用的格式
            builder.serializerByType(BigDecimal.class, ToStringSerializer.instance);

            // 配置 Jackson 序列化 long类型为String，解决后端返回的Long类型在前端精度丢失的问题
            builder.serializerByType(BigInteger.class, BigNumberSerializer.INSTANCE);
            builder.serializerByType(Long.class, BigNumberSerializer.INSTANCE);
            builder.serializerByType(Long.TYPE, BigNumberSerializer.INSTANCE);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(CommonTools.DATE_PATTERN);
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(CommonTools.TIME_PATTERN);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(CommonTools.DATE_TIME_PATTERN);

            // 设置 LocalDate 和 LocalDateTime 反序列化器
            builder.deserializers(new LocalDateDeserializer(dateFormatter));
            builder.deserializers(new LocalTimeDeserializer(timeFormatter));
            builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));

            // 设置 LocalDate 和 LocalDateTime 序列化器
            builder.serializers(new LocalDateSerializer(dateFormatter));
            builder.serializers(new LocalTimeSerializer(timeFormatter));
            builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
        };
    }

    private static final ObjectMapper om = new ObjectMapper();

    static {
        // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
        // Include.NON_NULL 属性为NULL 不序列化
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 允许出现单引号
        om.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

//        // 字段保留，将null值转为""
//        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//            @Override
//            public void serialize(
//                    Object o,
//                    JsonGenerator jsonGenerator,
//                    SerializerProvider serializerProvider
//            ) throws IOException {
//                jsonGenerator.writeString("");
//            }
//        });

        // 允许对象忽略未知属性
        // 序列化时，对象为 null，是否抛异常
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 反序列化时，json 中包含 pojo 不存在属性时，是否抛异常
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        om.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
        // 空字符串""必须是要绑定到一个POJO、Map、Cpllection、数组这样的对象上（而不是普通的基本类型和String类型）时，才会在反序列化时转为null。
        // om.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        // 序列化设置 关闭日志输出为时间戳的设置
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        //必须设置，否则无法将JSON转化为对象，会转化成Map类型
        // om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        om.setDateFormat(new SimpleDateFormat(CommonTools.DATE_TIME_PATTERN));

        // 设置序列化反序列化采用直接处理字段的方式， 不依赖setter 和 getter
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        om.registerModule(new ParameterNamesModule());
        om.registerModule(new Jdk8Module());

        // long 转字符串
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(BigInteger.class, BigNumberSerializer.INSTANCE);
        om.registerModule(simpleModule);

        // 时间日期
        om.registerModule(CommonTools.timeModule());
        om.setTimeZone(TimeZone.getDefault());

        // 自动查找并注册Java 8相关模块
        om.findAndRegisterModules();
    }

    public static JavaType makeJavaType(Class<?> parametrized, Class<?>... parameterClasses) {
        return om.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    public static JavaType makeJavaType(Class<?> rawType, JavaType... parameterTypes) {
        return om.getTypeFactory().constructParametricType(rawType, parameterTypes);
    }

    public static String toString(Object value) {
        if (Objects.isNull(value)) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        return toJSONString(value);
    }

    @SneakyThrows
    public static String toJSONString(Object value) {
        return om.writeValueAsString(value);
    }

    @SneakyThrows
    public static String toPrettyString(Object value) {
        return om.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }

    @SneakyThrows
    public static JsonNode fromJavaObject(Object value) {
        JsonNode result = null;
        if (Objects.nonNull(value) && (value instanceof String)) {
            result = parseObject((String) value);
        } else {
            result = om.valueToTree(value);
        }
        return result;
    }

    @SneakyThrows
    public static JsonNode parseObject(String content) {
        return om.readTree(content);
    }

    public static JsonNode getJsonElement(JsonNode node, String name) {
        return node.get(name);
    }

    public static JsonNode getJsonElement(JsonNode node, int index) {
        return node.get(index);
    }

    @SneakyThrows
    public static <T> T toJavaObject(TreeNode node, Class<T> clazz) {
        return om.treeToValue(node, clazz);
    }

    @SneakyThrows
    public static <T> T toJavaObject(TreeNode node, JavaType javaType) {
        return om.convertValue(node, javaType);
    }

    @SneakyThrows
    public static <T> T toJavaObject(TreeNode node, TypeReference<?> typeReference) {
        @SuppressWarnings("unchecked")
        T v = (T) om.convertValue(node, typeReference);
        return v;
    }

    public static <T> T toJavaObject(TreeNode node, Type type) {
        return toJavaObject(node, om.constructType(type));
    }

    public static <E> List<E> toJavaList(TreeNode node, Class<E> clazz) {
        return toJavaObject(node, makeJavaType(List.class, clazz));
    }

    public static List<Object> toJavaList(TreeNode node) {
        return toJavaObject(node, new TypeReference<List<Object>>() {
        });
    }

    public static <V> Map<String, V> toJavaMap(TreeNode node, Class<V> clazz) {
        return toJavaObject(node, makeJavaType(Map.class, String.class, clazz));
    }

    public static Map<String, Object> toJavaMap(TreeNode node) {
        return toJavaObject(node, new TypeReference<Map<String, Object>>() {
        });
    }

    @SneakyThrows
    public static <T> T toJavaObject(String content, Class<T> clazz) {
        return om.readValue(content, clazz);
    }

    @SneakyThrows
    public static <T> T toJavaObject(String content, JavaType javaType) {
        return om.readValue(content, javaType);
    }

    @SneakyThrows
    public static <T> T toJavaObject(String content, TypeReference<?> typeReference) {
        @SuppressWarnings("unchecked")
        T v = (T) om.readValue(content, typeReference);
        return v;
    }

    public static <T> T toJavaObject(String content, Type type) {
        return toJavaObject(content, om.constructType(type));
    }

    public static <E> List<E> toJavaList(String content, Class<E> clazz) {
        return toJavaObject(content, makeJavaType(List.class, clazz));
    }

    public static List<Object> toJavaList(String content) {
        return toJavaObject(content, new TypeReference<List<Object>>() {
        });
    }

    public static <V> Map<String, V> toJavaMap(String content, Class<V> clazz) {
        return toJavaObject(content, makeJavaType(Map.class, String.class, clazz));
    }

    public static Map<String, Object> toJavaMap(String content) {
        return toJavaObject(content, new TypeReference<Map<String, Object>>() {
        });
    }

    public static <T> Map<String, List<T>> toJavaMapList(String content) {
        Map<String, Object> map = toJavaMap(content);
        if (CommonUtils.isEmpty(map)) {
            return null;
        }
        return map.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> {
                                    if (ObjectUtils.isEmpty(entry.getValue())) {
                                        return new ArrayList<>();
                                    }
                                    @SuppressWarnings("unchecked")
                                    List<T> val = (List<T>) entry.getValue();
                                    return val;
                                },
                                (oldVal, newVal) -> newVal
                        )
                );
    }
}


