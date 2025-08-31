package com.supsp.springboot.core.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
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
import com.supsp.springboot.core.utils.CommonTools;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
public class JacksonConfig {

    @Bean
    public SensitiveFieldAnnotationIntrospector sensitiveFieldAnnotationIntrospector() {
        return new SensitiveFieldAnnotationIntrospector();
    }

    @Bean
    public SensitiveSerializer<?> sensitiveFieldSerializer() {
        return new SensitiveSerializer<>();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
        // Include.NON_NULL 属性为NULL 不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 允许出现单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        // 允许对象忽略未知属性
        // 序列化时，对象为 null，是否抛异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 反序列化时，json 中包含 pojo 不存在属性时，是否抛异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
        // 空字符串""必须是要绑定到一个POJO、Map、Cpllection、数组这样的对象上（而不是普通的基本类型和String类型）时，才会在反序列化时转为null。
        // objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        // 序列化设置 关闭日志输出为时间戳的设置
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        //必须设置，否则无法将JSON转化为对象，会转化成Map类型
        // objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setDateFormat(new SimpleDateFormat(CommonTools.DATE_TIME_PATTERN));

        // 设置序列化反序列化采用直接处理字段的方式， 不依赖setter 和 getter
         // objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
         // objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new Jdk8Module());

        // long 转字符串 解决后端返回的Long类型在前端精度丢失的问题
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, BigNumberSerializer.INSTANCE);
        simpleModule.addSerializer(Long.TYPE, BigNumberSerializer.INSTANCE);
        simpleModule.addSerializer(BigInteger.class, BigNumberSerializer.INSTANCE);
        objectMapper.registerModule(simpleModule);

        AnnotationIntrospector annotationIntrospector = objectMapper.getSerializationConfig().getAnnotationIntrospector();
        AnnotationIntrospector newIntro = AnnotationIntrospectorPair.pair(annotationIntrospector, sensitiveFieldAnnotationIntrospector());
        objectMapper.setAnnotationIntrospector(newIntro);

        // 时间日期
        objectMapper.registerModule(CommonTools.timeModule());
        objectMapper.setTimeZone(TimeZone.getDefault());

        // 自动查找并注册Java 8相关模块
        objectMapper.findAndRegisterModules();

        return objectMapper;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {

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

            // 配置 Jackson 序列化 BigDecimal 时使用的格式
            builder.serializerByType(BigDecimal.class, ToStringSerializer.instance);

            // 配置 Jackson 序列化 long类型为String，解决后端返回的Long类型在前端精度丢失的问题
            builder.serializerByType(BigInteger.class, BigNumberSerializer.INSTANCE);
            builder.serializerByType(Long.class, BigNumberSerializer.INSTANCE);
            builder.serializerByType(Long.TYPE, BigNumberSerializer.INSTANCE);

        };
    }
}
