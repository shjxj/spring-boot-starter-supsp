package com.supsp.springboot.core.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.supsp.springboot.core.utils.CommonTools;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.utils.RedisUtils;
import com.supsp.springboot.core.utils.StrUtils;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Configuration
@EnableTransactionManagement
public class RedisConfig {

    @Resource
    private CoreProperties coreProperties;

    static class PrefixRedisSerializer extends StringRedisSerializer {
        private String getPrefix() {
            return CommonUtils.getPrefix();
        }

        @Override
        public String deserialize(byte[] bytes) {
            String decode = RedisUtils.prefixDecode(bytes, getPrefix());
            return super.deserialize(StrUtils.isBlank(decode) ? null : decode.getBytes());
        }

        @Override
        public byte[] serialize(String string) {
            return super.serialize(RedisUtils.prefixEncode(string, getPrefix()));
        }
    }

    @Bean
    public RedisTemplate<Serializable, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisSerializer<Object> redisSerializer = redisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        RedisTemplate<Serializable, Object> redisTemplate = new RedisTemplate<>();
        // redisTemplate.setEnableTransactionSupport(true);

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new PrefixRedisSerializer());

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.afterPropertiesSet();

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisSerializer<Object> redisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
        // Include.NON_NULL 属性为NULL 不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // 允许出现单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

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
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setDateFormat(new SimpleDateFormat(CommonTools.DATE_TIME_PATTERN));

        // 设置序列化反序列化采用直接处理字段的方式， 不依赖setter 和 getter
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new Jdk8Module());

        // long 转字符串 解决后端返回的Long类型在前端精度丢失的问题
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, BigNumberSerializer.INSTANCE);
        simpleModule.addSerializer(Long.TYPE, BigNumberSerializer.INSTANCE);
        simpleModule.addSerializer(BigInteger.class, BigNumberSerializer.INSTANCE);
        objectMapper.registerModule(simpleModule);

        // 时间日期
        objectMapper.registerModule(CommonTools.timeModule());
        objectMapper.setTimeZone(TimeZone.getDefault());

        // 自动查找并注册Java 8相关模块
        objectMapper.findAndRegisterModules();

        //创建JSON序列化器
        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }
}
