package com.supsp.springboot.core.utils;

import com.supsp.springboot.core.exceptions.DataException;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@Slf4j
public class RedisUtils {

    @Getter
    private static RedisTemplate<Serializable, Object> redisTemplate;

    /**
     * 增加前缀
     *
     * @param str
     * @param prefix
     * @return
     */
    public static String prefixEncode(String str, String prefix) {
        return CommonUtils.prefixEncode(str, prefix);
    }

    /**
     * 移除前缀
     *
     * @param bytes
     * @param prefix
     * @return
     */
    public static String prefixDecode(byte[] bytes, String prefix) {
        return CommonUtils.prefixDecode(bytes, prefix);
    }

    public RedisUtils(RedisTemplate<Serializable, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    /* ------------------- key 相关操作 --------------------- */

    /**
     * 删除 key
     *
     * @param key
     * @return
     */
    public static boolean delete(String key) {
        Boolean res = redisTemplate.delete(key);
        return CommonUtils.isTrue(res);
    }

    /**
     * 删除 key
     *
     * @param keys
     * @return
     */
    public static Long delete(Collection<Serializable> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 判断 key 是否存在
     *
     * @param key
     * @return
     */
    public static boolean hasKey(String key) {
        Boolean res = redisTemplate.hasKey(key);
        return CommonUtils.isTrue(res);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    public static boolean expire(String key, long timeout, TimeUnit unit) {
        Boolean res = redisTemplate.expire(key, timeout, unit);
        return CommonUtils.isTrue(res);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param timeout
     * @return
     */
    public static boolean expire(String key, long timeout) {
        Boolean res = redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        return CommonUtils.isTrue(res);
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param date
     * @return
     */
    public static boolean expireAt(String key, Date date) {
        Boolean res = redisTemplate.expireAt(key, date);
        return CommonUtils.isTrue(res);
    }

    /**
     * 移除 key 的过期时间，key 将持久保持
     *
     * @param key
     * @return
     */
    public static boolean persist(String key) {
        Boolean res = redisTemplate.persist(key);
        return CommonUtils.isTrue(res);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key
     * @param unit
     * @return
     */
    public static Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key
     * @return
     */
    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 修改 key 的名称
     *
     * @param oldKey
     * @param newKey
     */
    public static void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 仅当 newkey 不存在时，将 oldKey 改名为 newkey
     *
     * @param oldKey
     * @param newKey
     * @return
     */
    public static boolean renameIfAbsent(String oldKey, String newKey) {
        Boolean res = redisTemplate.renameIfAbsent(oldKey, newKey);
        return CommonUtils.isTrue(res);
    }

    /**
     * 返回 key 所储存的值的类型
     *
     * @param key
     * @return
     */
    public static DataType type(String key) {
        return redisTemplate.type(key);
    }

    public static Set<Serializable> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public static Cursor<Serializable> scan(ScanOptions options) {
        return redisTemplate.scan(options);
    }

    public static Cursor<Serializable> scan(
            String pattern,
            Long count
    ) {
        return scan(
                ScanOptions.scanOptions()
                        .match(pattern)
                        .count(count)
                        .build()
        );
    }

    public static Cursor<Serializable> scan(
            byte[] pattern,
            Long count
    ) {
        return scan(
                ScanOptions.scanOptions()
                        .match(pattern)
                        .count(count)
                        .build()
        );
    }

    public static Cursor<Serializable> scan(
            String pattern,
            Long count,
            DataType type
    ) {
        return scan(
                ScanOptions.scanOptions()
                        .match(pattern)
                        .count(count)
                        .type(type)
                        .build()
        );
    }

    public static Cursor<Serializable> scan(
            byte[] pattern,
            Long count,
            DataType type
    ) {
        return scan(
                ScanOptions.scanOptions()
                        .match(pattern)
                        .count(count)
                        .type(type)
                        .build()
        );
    }

    public static Set<Serializable> scanKeys(ScanOptions options) {
        Set<Serializable> keys = new HashSet<>();
        Cursor<Serializable> cursor = scan(options);
        while (cursor.hasNext()) {
            keys.add(cursor.next());
        }
        return keys;
    }

    public static Set<Serializable> scanKeys(
            String pattern,
            Long count
    ) {
        return scanKeys(
                ScanOptions.scanOptions()
                        .match(pattern)
                        .count(count)
                        .build()
        );
    }

    public static Set<Serializable> scanKeys(
            byte[] pattern,
            Long count
    ) {
        return scanKeys(
                ScanOptions.scanOptions()
                        .match(pattern)
                        .count(count)
                        .build()
        );
    }

    public static Set<Serializable> scanKeys(
            String pattern,
            Long count,
            DataType type
    ) {
        return scanKeys(
                ScanOptions.scanOptions()
                        .match(pattern)
                        .count(count)
                        .type(type)
                        .build()
        );
    }

    public static Set<Serializable> scanKeys(
            byte[] pattern,
            Long count,
            DataType type
    ) {
        return scanKeys(
                ScanOptions.scanOptions()
                        .match(pattern)
                        .count(count)
                        .type(type)
                        .build()
        );
    }

    /* ------------------- string 相关操作 --------------------- */

    /**
     * string set
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * string set with timeout
     *
     * @param key
     * @param value
     * @param timeout
     * @param unit
     */
    public static void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * string set with timeout
     *
     * @param key
     * @param value
     * @param timeout
     */
    public static void set(String key, Object value, long timeout) {
        set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * string set with timeout
     *
     * @param key
     * @param value
     * @param timeout
     */
    public static void set(String key, Object value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    public static boolean setNx(String key, Object value) {
        Boolean res = redisTemplate.opsForValue().setIfAbsent(key, value, Duration.ofSeconds(0));
        return CommonUtils.isTrue(res);
    }

    public static boolean setNx(String key, Object value, long timeout, TimeUnit unit) {
        Boolean res = redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
        return CommonUtils.isTrue(res);
    }

    public static boolean setNx(String key, Object value, Duration timeout) {
        Boolean res = redisTemplate.opsForValue().setIfAbsent(key, value, timeout);
        return CommonUtils.isTrue(res);
    }

    /**
     * string get
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T get(String key) {
        try {
            return (T) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("exception message", e);
        }
        return null;
    }

    /**
     * 返回 key 中字符串值的子字符
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static String getRange(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     *
     * @param key
     * @param value
     * @return
     */
    public static Object getAndSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)
     *
     * @param key
     * @param offset
     * @return
     */
    public static boolean getBit(String key, long offset) {
        Boolean res = redisTemplate.opsForValue().getBit(key, offset);
        return CommonUtils.isTrue(res);
    }

    /**
     * 批量获取
     *
     * @param keys
     * @return
     */
    public static <V> List<V> multiGet(Collection<Serializable> keys) {
        try {
            return (List<V>) redisTemplate.opsForValue().multiGet(keys);
        } catch (Exception e) {
            // log
        }
        return null;
    }

    /**
     * 设置ASCII码, 字符串'a'的ASCII码是97, 转为二进制是'01100001', 此方法是将二进制第offset位值变为value
     *
     * @param key   位置
     * @param value 值,true为1, false为0
     * @return
     */
    public static boolean setBit(String key, long offset, boolean value) {
        Boolean res = redisTemplate.opsForValue().setBit(key, offset, value);
        return CommonUtils.isTrue(res);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     *
     * @param key
     * @param value
     * @param timeout 过期时间
     * @param unit    时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES
     *                秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
     */
    public static void setEx(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 只有在 key 不存在时设置 key 的值
     *
     * @param key
     * @param value
     * @return 之前已经存在返回false, 不存在返回true
     */
    public static boolean setIfAbsent(String key, String value) {
        Boolean res = redisTemplate.opsForValue().setIfAbsent(key, value);
        return CommonUtils.isTrue(res);
    }

    /**
     * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
     *
     * @param key
     * @param value
     * @param offset 从指定位置开始覆写
     */
    public static void setRange(String key, String value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 获取字符串的长度
     *
     * @param key
     * @return
     */
    public static Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 批量添加
     *
     * @param maps
     */
    public static void multiSet(Map<String, String> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
     *
     * @param maps
     * @return 之前已经存在返回false, 不存在返回true
     */
    public static boolean multiSetIfAbsent(Map<String, String> maps) {
        Boolean res = redisTemplate.opsForValue().multiSetIfAbsent(maps);
        return CommonUtils.isTrue(res);
    }

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key
     * @return
     */
    public static Long incrBy(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * @param key
     * @return
     */
    public static Double incrByFloat(String key, double increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 追加到末尾
     *
     * @param key
     * @param value
     * @return
     */
    public static Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }


    /**
     * 自增
     *
     * @param key
     * @param expire
     * @return
     */
    public static long getIncr(@NotBlank String key, long expire) {
        key = prefixEncode(key, CommonUtils.getPrefix());
        assert key != null : "key应非null";
        RedisAtomicLong counter =
                new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        counter.expire(expire, TimeUnit.SECONDS);
        return counter.incrementAndGet();
    }

    /* ------------------- hash 相关操作 --------------------- */

    /**
     * 获取存储在哈希表中指定字段的值
     *
     * @param key
     * @param field
     * @return
     */
    public static Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key
     * @return
     */
    public static Map<Object, Object> hGetAll(Serializable key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key
     * @param fields
     * @return
     */
    public static List<Object> hMultiGet(String key, Collection<Object> fields) {
        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    public static <T> void hPut(String key, Serializable hashKey, T value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public static void hPut(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public static <V> void hPutAll(String key, Map<String, V> maps) {
        redisTemplate.opsForHash().putAll(key, maps);
    }

    /**
     * 仅当hashKey不存在时才设置
     *
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public static boolean hPutIfAbsent(String key, String hashKey, String value) {
        Boolean res = redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
        return CommonUtils.isTrue(res);
    }

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key
     * @param fields
     * @return
     */
    public static Long hDelete(String key, Object... fields) {
        return redisTemplate.opsForHash().delete(key, Arrays.toString(fields));
    }

    public static Long hDelete(String key, Collection<?> fields) {
        return redisTemplate.opsForHash().delete(key, fields.stream().map(String::valueOf).distinct().toArray());
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key
     * @param field
     * @return
     */
    public static boolean hExists(String key, Object field) {
        Boolean res = redisTemplate.opsForHash().hasKey(key, field);
        return CommonUtils.isTrue(res);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key
     * @param field
     * @param increment
     * @return
     */
    public static <HK> Long hIncrBy(String key, HK field, long increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    public static <HK> Long hDecrBy(String key, HK field, long increment) {
        return redisTemplate.opsForHash().increment(key, field, -increment);
    }

    /**
     * 为哈希表 key 中的指定字段的整数值加上增量 increment
     *
     * @param key
     * @param field
     * @param delta
     * @return
     */
    public static <HK> Double hIncrByFloat(String key, HK field, double delta) {
        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    public static <HK> Double hDecrByFloat(String key, HK field, double delta) {
        return redisTemplate.opsForHash().increment(key, field, -delta);
    }

    /**
     * 获取所有哈希表中的字段
     *
     * @param key
     * @return
     */
    public static Set<Object> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取哈希表中字段的数量
     *
     * @param key
     * @return
     */
    public static Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 获取哈希表中所有值
     *
     * @param key
     * @return
     */
    public static List<Object> hValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 迭代哈希表中的键值对
     *
     * @param key
     * @param options
     * @return
     */
    public static Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options) {
        return redisTemplate.opsForHash().scan(key, options);
    }

    /* ------------------- list 相关操作 --------------------- */

    /**
     * 通过索引获取列表中的元素
     *
     * @param key
     * @param index
     * @return
     */
    public static Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取列表指定范围内的元素
     *
     * @param key
     * @param start 开始位置, 0是开始位置
     * @param end   结束位置, -1返回所有
     * @return
     */
    public static List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 存储在list头部
     *
     * @param key
     * @param value
     * @return
     */
    public static Long lLeftPush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public static Long lLeftPushAll(String key, String... value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public static Long lLeftPushAll(String key, Collection<String> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 当list存在的时候才加入
     *
     * @param key
     * @param value
     * @return
     */
    public static Long lLeftPushIfPresent(String key, String value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 如果pivot存在,再pivot前面添加
     *
     * @param key
     * @param pivot
     * @param value
     * @return
     */
    public static Long lLeftPush(String key, String pivot, String value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public static Long lRightPush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public static Long lRightPushAll(String key, String... value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public static Long lRightPushAll(String key, Collection<String> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 为已存在的列表添加值
     *
     * @param key
     * @param value
     * @return
     */
    public static Long lRightPushIfPresent(String key, String value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 在pivot元素的右边添加值
     *
     * @param key
     * @param pivot
     * @param value
     * @return
     */
    public static Long lRightPush(String key, String pivot, String value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 通过索引设置列表元素的值
     *
     * @param key
     * @param index 位置
     * @param value
     */
    public static void lSet(String key, long index, String value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移出并获取列表的第一个元素
     *
     * @param key
     * @return 删除的元素
     */
    public static Object lLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return
     */
    public static Object lBLeftPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key
     * @return 删除的元素
     */
    public static Object lRightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return
     */
    public static Object lBRightPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     *
     * @param sourceKey
     * @param destinationKey
     * @return
     */
    public static Object lRightPopAndLeftPush(String sourceKey, String destinationKey) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param sourceKey
     * @param destinationKey
     * @param timeout
     * @param unit
     * @return
     */
    public static Object lBRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
    }

    /**
     * 删除集合中值等于value得元素
     *
     * @param key
     * @param index index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     *              index<0, 从尾部开始删除第一个值等于value的元素;
     * @param value
     * @return
     */
    public static Long lRemove(String key, long index, String value) {
        return redisTemplate.opsForList().remove(key, index, value);
    }

    /**
     * 裁剪list
     *
     * @param key
     * @param start
     * @param end
     */
    public static void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取列表长度
     *
     * @param key
     * @return
     */
    public static Long lLen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /* ------------------- set 相关操作 --------------------- */

    /**
     * set添加元素
     *
     * @param key
     * @param values
     * @return
     */
    public static Long sAdd(String key, String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * set移除元素
     *
     * @param key
     * @param values
     * @return
     */
    public static Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 移除并返回集合的一个随机元素
     *
     * @param key
     * @return
     */
    public static Object sPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 将元素value从一个集合移到另一个集合
     *
     * @param key
     * @param value
     * @param destKey
     * @return
     */
    public static boolean sMove(String key, String value, String destKey) {
        Boolean res = redisTemplate.opsForSet().move(key, value, destKey);
        return CommonUtils.isTrue(res);
    }

    /**
     * 获取集合的大小
     *
     * @param key
     * @return
     */
    public static Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断集合是否包含value
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean sIsMember(String key, Object value) {
        Boolean res = redisTemplate.opsForSet().isMember(key, value);
        return CommonUtils.isTrue(res);
    }

    /**
     * 获取两个集合的交集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public static Set<Object> sIntersect(String key, String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的交集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public static Set<Object> sIntersect(Serializable key, Collection<Serializable> otherKeys) {
        return redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的交集存储到destKey集合中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public static Long sIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的交集存储到destKey集合中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public static Long sIntersectAndStore(Serializable key, Collection<Serializable> otherKeys, Serializable destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取两个集合的并集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public static Set<Object> sUnion(String key, String otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * 获取key集合与多个集合的并集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public static Set<Object> sUnion(Serializable key, Collection<Serializable> otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的并集存储到destKey中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public static Long sUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的并集存储到destKey中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public static Long sUnionAndStore(Serializable key, Collection<Serializable> otherKeys, Serializable destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取两个集合的差集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public static Set<Object> sDifference(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的差集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public static Set<Object> sDifference(Serializable key, Collection<Serializable> otherKeys) {
        return redisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的差集存储到destKey中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public static Long sDifference(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的差集存储到destKey中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public static Long sDifference(Serializable key, Collection<Serializable> otherKeys, Serializable destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取集合所有元素
     *
     * @param key
     * @return
     */
    public static Set<Object> setMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取集合中的一个元素
     *
     * @param key
     * @return
     */
    public static Object sRandomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取集合中count个元素
     *
     * @param key
     * @param count
     * @return
     */
    public static List<Object> sRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取集合中count个元素并且去除重复的
     *
     * @param key
     * @param count
     * @return
     */
    public static Set<Object> sDistinctRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * @param key
     * @param options
     * @return
     */
    public static Cursor<Object> sScan(String key, ScanOptions options) {
        return redisTemplate.opsForSet().scan(key, options);
    }

    /* ------------------- zSet 相关操作 --------------------- */

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    public static boolean zAdd(String key, String value, double score) {
        Boolean res = redisTemplate.opsForZSet().add(key, value, score);
        return CommonUtils.isTrue(res);
    }

    /**
     * @param key
     * @param values
     * @return
     */
    public static Long zAdd(Serializable key, Set<ZSetOperations.TypedTuple<Object>> values) {
        return redisTemplate.opsForZSet().add(key, values);
    }

    /**
     * @param key
     * @param values
     * @return
     */
    public static Long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 增加元素的score值，并返回增加后的值
     *
     * @param key
     * @param value
     * @param delta
     * @return
     */
    public static Double zIncrementScore(String key, String value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
     *
     * @param key
     * @param value
     * @return 0表示第一位
     */
    public static Long zRank(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列
     *
     * @param key
     * @param value
     * @return
     */
    public static Long zReverseRank(String key, Object value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取集合的元素, 从小到大排序
     *
     * @param key
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return
     */
    public static Set<Object> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 获取集合元素, 并且把score值也获取
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 根据Score值查询集合元素
     *
     * @param key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static Set<Object> zRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @return
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(
            String key,
            double min,
            double max,
            long start,
            long end
    ) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<Object> zReverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zReverseRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Set<Object> zReverseRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zReverseRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * @param key
     * @param min
     * @param max
     * @param start
     * @param end
     * @return
     */
    public static Set<Object> zReverseRangeByScore(String key, double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, start, end);
    }

    /**
     * 根据score值获取集合元素数量
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Long zCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 获取集合大小
     *
     * @param key
     * @return
     */
    public static Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取集合大小
     *
     * @param key
     * @return
     */
    public static Long zZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 获取集合中value元素的score值
     *
     * @param key
     * @param value
     * @return
     */
    public static Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 移除指定索引位置的成员
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Long zRemoveRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 根据指定的score值的范围来移除成员
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Long zRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public static Long zUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public static Long zUnionAndStore(Serializable key, Collection<Serializable> otherKeys, Serializable destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 交集
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public static Long zIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * 交集
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public static Long zIntersectAndStore(Serializable key, Collection<Serializable> otherKeys, Serializable destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }

    /**
     * @param key
     * @param options
     * @return
     */
    public static Cursor<ZSetOperations.TypedTuple<Object>> zScan(String key, ScanOptions options) {
        return redisTemplate.opsForZSet().scan(key, options);
    }

    /**
     * 批量操作
     *
     * @param function
     * @param <R>
     * @return
     * @throws DataAccessException
     */
    @SuppressWarnings({"unchecked"})
    public static <R> R multi(Function<RedisOperations<Serializable, Object>, R> function) throws DataException {
        if (function == null) {
            return null;
        }
        return redisTemplate.execute(new SessionCallback<>() {
            @Override
            public <K, V> R execute(
                    @Nonnull RedisOperations<K, V> operations
            ) throws DataAccessException {
                redisTemplate.setEnableTransactionSupport(true);
                operations.multi();
                try {
                    R res = function.apply((RedisOperations<Serializable, Object>) operations);
                    redisTemplate.exec();
                    return res;
                } catch (Exception e) {
                    redisTemplate.discard();
                    throw new DataException(e.getMessage());
                }
            }
        });
    }

}
