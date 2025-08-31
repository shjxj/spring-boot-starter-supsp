package com.supsp.springboot.core.utils;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.supsp.springboot.core.base.ActionResult;
import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.consts.Constants;
import com.supsp.springboot.core.enums.FileType;
import com.supsp.springboot.core.vo.AreaVo;
import com.supsp.springboot.core.vo.OrgKindVo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CommonUtils {

    public static final String BASE_RANDOM_STRING = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

    @Resource
    private CoreProperties coreProperties;

    public static String getPrefix() {
        String prefix = CoreProperties.PREFIX;
        if (StrUtils.isNotBlank(CoreProperties.PROFILE_ACTIVE)) {
            prefix += Constants.TEXT_SPLIT + CoreProperties.PROFILE_ACTIVE;
        }
        return prefix;
    }

    public static String prefixEncode(String str, String prefix) {
        if (StrUtils.isBlank(prefix)) {
            return str;
        }
        if (StrUtils.isBlank(str)) {
            return null;
        }
        return prefix + Constants.TEXT_SPLIT + str;
    }

    public static String prefixEncode(String str) {
        return prefixEncode(str, getPrefix());
    }

    public static String prefixDecode(byte[] bytes, String prefix) {
        String string = bytes == null ? null : new String(bytes);
        if (StrUtils.isBlank(string)) {
            return null;
        }
        if (StrUtils.isBlank(prefix)) {
            return string;
        }

        if (!StrUtils.startsWithIgnoreCase(string, prefix + Constants.TEXT_SPLIT)) {
            return string;
        }
        //return string;
        return StrUtils.substring(string, prefix.trim().length() + Constants.TEXT_SPLIT.length());
    }

    public static String prefixDecode(byte[] bytes) {
        return prefixDecode(bytes, getPrefix());
    }

    static final TemplateParserContext TEMPLATE_PARSER_CONTEXT = new TemplateParserContext("${", "}");

    static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    public static long timestamp() {
        return Instant.now().getEpochSecond();
    }

    public static int timestampInt() {
        return (int) Instant.now().getEpochSecond();
    }

    public static long timestampMilli() {
        return Instant.now().getLong(ChronoField.MILLI_OF_SECOND);
    }

    public static boolean isEl(String param) {
        return param.startsWith("${");
    }

    public static Object parse(String expressionString, Method method, Object[] args) {
        if (StrUtils.isBlank(expressionString)) {
            return expressionString;
        }

        StandardReflectionParameterNameDiscoverer discoverer = new StandardReflectionParameterNameDiscoverer();
        String[] paraNameArr = discoverer.getParameterNames(method);
        if (ArrayUtils.isEmpty(paraNameArr)) {
            return expressionString;
        }

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();

        try {
            for (int i = 0; i < Objects.requireNonNull(paraNameArr).length; i++) {
                evaluationContext.setVariable(paraNameArr[i], args[i]);
            }
            return EXPRESSION_PARSER.parseExpression(expressionString, TEMPLATE_PARSER_CONTEXT).getValue(evaluationContext, String.class);
        } catch (Exception e) {
            // log.error();
        }
        return expressionString;
//        if (StrUtils.isBlank(expressionString)) {
//            return null;
//        }
//        //获取被拦截方法参数名列表
//        StandardReflectionParameterNameDiscoverer discoverer = new StandardReflectionParameterNameDiscoverer();
//        String[] paramNameArr = discoverer.getParameterNames(method);
//        //SPEL解析
//        ExpressionParser parser = new SpelExpressionParser();
//        EvaluationContext context = new StandardEvaluationContext();
//        try {
//            if (Objects.requireNonNull(paramNameArr).length > 0) {
//                for (int i = 0; i < Objects.requireNonNull(paramNameArr).length; i++) {
//                    context.setVariable(paramNameArr[i], args[i]);
//                }
//            }
//            Expression expression = parser.parseExpression(expressionString, new TemplateParserContext());
//            return expression.getValue(context, String.class);
//
//            // return parser.parseExpression(expressionString).getValue(context, new TemplateParserContext());
//        } catch (Exception e) {
//            //
//        }
//        return expressionString;
    }

    public static String parse(String elString, TreeMap<String, Object> map) {
        elString = String.format("#{%s}", elString);
        //创建表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        //通过evaluationContext.setVariable可以在上下文中设定变量。
        EvaluationContext context = new StandardEvaluationContext();
        map.forEach(context::setVariable);

        //解析表达式
        Expression expression = parser.parseExpression(elString, new TemplateParserContext());
        //使用Expression.getValue()获取表达式的值，这里传入了Evaluation上下文
        return expression.getValue(context, String.class);
    }

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return CollectionUtils.isEmpty(map);
    }

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static <T> List<T> removeDuplicate(List<T> data) {
        if (CommonUtils.isEmpty(data)) {
            return null;
        }
        return new ArrayList<T>(new LinkedHashSet<T>(data));
    }

    public static Long toLong(String value) {
        if (StrUtils.isBlank(value)) {
            return null;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Long toLong(long value) {
        try {
            return Long.parseLong(String.valueOf(value));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static long longValue(Object value) {
        if (value == null) {
            return Constants.LONG_ZERO;
        }
        try {
            return Long.parseLong(String.valueOf(value));
        } catch (NumberFormatException ex) {
            // return null;
        }
        return Constants.LONG_ZERO;
    }

    public static long longValue(Long value) {
        return value == null ? 0 : value;
    }

    public static long longValue(String value) {
        Long val = toLong(value);
        return val == null ? 0 : val;
    }

    public static Integer toInteger(String value) {
        if (StrUtils.isBlank(value)) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Integer toInteger(Long value) {
        try {
            return Math.toIntExact(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Integer toInteger(int value) {
        try {
            return Integer.parseInt(String.valueOf(value));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static int integerValue(Integer value) {
        return value == null ? 0 : value;
    }

    public static Short toShort(String value) {
        if (StrUtils.isBlank(value)) {
            return null;
        }
        try {
            return Short.parseShort(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Short toShort(short value) {
        try {
            return Short.parseShort(String.valueOf(value));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static BigDecimal toBigDecimal(String value) {
        if (StrUtils.isBlank(value)) {
            return null;
        }
        try {
            return NumberUtils.createBigDecimal(value);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static short shortValue(Short value) {
        return value == null ? 0 : value;
    }

    public static boolean isID(String id) {
        return isID(toLong(id));
    }

    public static boolean isID(Long id) {
        return id != null && id.compareTo(Constants.LONG_ZERO) > 0;
    }

    public static boolean isID(long id) {
        return id > 0;
    }

    public static boolean isNotID(long id) {
        return !isID(id);
    }

    public static boolean isNotID(String id) {
        return !isID(id);
    }

    public static boolean isNotID(Long id) {
        return !isID(id);
    }

    public static long getID(String id) {
        if (StrUtils.isBlank(id)) {
            return 0;
        }
        Long value = toLong(id);
        if (!isID(value)) {
            return 0;
        }
        return longValue(value);
    }

    public static long getID(Object id) {
        if (id == null) {
            return 0;
        }
        return getID(String.valueOf(id));
    }

    public static long getID(Long id) {
        return isID(id) ? longValue(id) : 0;
    }

    public static String fileBaseContentType(@Nullable String contentType) {
        if (StrUtils.isBlank(contentType)) {
            return "";
        }
        String[] type = StrUtils.split(contentType, "/");
        return type[0].trim().toLowerCase();
    }

    public static FileType fileType(
            @Nullable String contentType,
            @Nullable String extName
    ) {
        if (StrUtils.isBlank(contentType) && StrUtils.isBlank(extName)) {
            return FileType.file;
        }
        String baseType = fileBaseContentType(contentType);
        if (StrUtils.isNotBlank(baseType)) {
            switch (baseType) {
                case "image" -> {
                    return FileType.image;
                }
                case "audio" -> {
                    return FileType.audio;
                }
                case "video" -> {
                    return FileType.video;
                }
            }
        }

        if (StrUtils.isNotBlank(extName)) {
            switch (extName.trim().toLowerCase()) {
                case "doc", "docx", "pdf", "xls", "xlsx", "ppt", "pptx" -> {
                    return FileType.doc;
                }
                case "jpg", "jpeg", "bmp", "gif", "png" -> {
                    return FileType.image;
                }
                case "aac", "mid", "midi", "mp3", "oga", "wav", "weba" -> {
                    return FileType.audio;
                }
                case "avi", "mpeg", "ogv", "webm", "3gp", "3g2" -> {
                    return FileType.video;
                }
            }
        }

        return FileType.file;
    }

    public static boolean isOrderSort(Integer value) {
        if (!NumUtils.gtZero(value)) {
            return false;
        }
        return value.compareTo(65535) <= 0;
    }

    public static String ascString(int start, int end) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = start; i <= end; i++) {
                sb.append((char) i);
            }
            return sb.toString();
        } catch (Exception e) {
            //
        }
        return null;
    }

    /**
     * 随机字符串
     *
     * @param count   长度
     * @param letters 生成的字符串是否包括字母字符
     * @param numbers 生成的字符串是否包含数字字符
     * @return
     */
    public static String random(int count, boolean letters, boolean numbers) {
        String string = "";
        if (letters) {
            string += RandomUtil.BASE_CHAR.toUpperCase() + RandomUtil.BASE_CHAR;
        }
        if (numbers) {
            string += RandomUtil.BASE_NUMBER;
        }
        if (StrUtils.isBlank(string)) {
            string = RandomUtil.BASE_CHAR_NUMBER;
        }
        return RandomUtil.randomString(string, count);
    }

    public static String random(int count) {
        return random(count, true, true);
    }

    public static String random(boolean numbers) {
        return random(8, true, numbers);
    }

    public static String random(int count, char... chars) {
        return RandomUtil.randomString(String.valueOf(chars), count);
    }

    /**
     * 生成随机[a-z,A-Z]字符串，包含大小写
     *
     * @param count
     * @return
     */
    public static String randomAlphabetic(int count) {
        return RandomUtil.randomString(RandomUtil.BASE_CHAR.toUpperCase() + RandomUtil.BASE_CHAR, count);
    }

    public static String randomAlphabetic(int minLengthInclusive, int maxLengthExclusive) {
        return randomAlphabetic(RandomUtil.randomInt(minLengthInclusive, maxLengthExclusive));
    }

    /**
     * 生成指定长度的字母和数字组成的随机组合字符串
     *
     * @param count
     * @return
     */
    public static String randomAlphanumeric(int count) {
        return RandomUtil.randomString(RandomUtil.BASE_CHAR_NUMBER, count);
    }

    public static String randomAlphanumeric() {
        return randomAlphanumeric(8);
    }

    /**
     * 创建一个随机字符串，其长度介于包含最小值和最大最大值之间,字符将从拉丁字母（a-z、A-Z）和数字0-9中选择。
     *
     * @param minLengthInclusive 要生成的字符串的包含最小长度
     * @param maxLengthExclusive 要生成的字符串的包含最大长度
     * @return
     */
    public static String randomAlphanumeric(int minLengthInclusive, int maxLengthExclusive) {
        return randomAlphanumeric(RandomUtil.randomInt(minLengthInclusive, maxLengthExclusive));
    }

    /**
     * 生成随机数字字符串
     *
     * @param count
     * @return
     */
    public static String randomNumeric(int count) {
        return RandomUtil.randomNumbers(count);
    }

    public static String randomNumeric(int minLengthInclusive, int maxLengthExclusive) {
        return randomNumeric(RandomUtil.randomInt(minLengthInclusive, maxLengthExclusive));
    }

    /**
     * 包含了标点符号、大小写字母、数字
     *
     * @param count
     * @return
     */
    public static String randomGraph(int count) {
        return RandomUtil.randomString(BASE_RANDOM_STRING, count);
    }

    public static String randomGraph(int minLengthInclusive, int maxLengthExclusive) {
        return randomGraph(RandomUtil.randomInt(minLengthInclusive, maxLengthExclusive));
    }

    /**
     * 生成ASCII值从32到126组成的随机字符串
     *
     * @param count
     * @return
     */
    public static String randomAscii(int count) {
        return RandomUtil.randomString(" " + BASE_RANDOM_STRING, count);
    }

    public static String randomAscii(int minLengthInclusive, int maxLengthExclusive) {
        return randomAscii(RandomUtil.randomInt(minLengthInclusive, maxLengthExclusive));
    }

    /**
     * ASCII中32-126的字符，包含了标点符号、大小写字母、数字和空格）
     *
     * @param count
     * @return
     */
    public static String randomPrint(int count) {
        return RandomUtil.randomString(" " + BASE_RANDOM_STRING, count);
    }

    public static String randomPrint(int minLengthInclusive, int maxLengthExclusive) {
        return randomPrint(RandomUtil.randomInt(minLengthInclusive, maxLengthExclusive));
    }

    /**
     * 生成编码
     *
     * @param count
     * @return
     */
    public static String randomCode(int count) {
        return randomAlphanumeric(count);
    }

    public static String randomCode() {
        return randomCode(16);
    }

    public static String randomCode(String prefix) {
        String code = randomCode(8);
        if (StrUtils.isNotBlank(prefix)) {
            code = prefix + code;
        }
        return code;
    }

    public static String randomCode(int count, String prefix) {
        String code = randomCode(count);
        if (StrUtils.isNoneBlank(prefix)) {
            code = prefix + code;
        }
        return code;
    }

    public static String randomProductCode(String prefix, int len) {
        int randomLen = len;
        if (StrUtils.isNotBlank(prefix)) {
            randomLen = len - prefix.trim().length();
        }
        if (randomLen < 10) {
            randomLen = 10;
        }
        return prefix + randomNumeric(randomLen);
    }

    public static String randomProductCode(int len) {
        return randomProductCode(Constants.PRODUCT_CODE_PREFIX, len);
    }

    public static String randomProductCode() {
        return randomProductCode(Constants.PRODUCT_CODE_PREFIX, Constants.PRODUCT_CODE_LEN);
    }

    public static String randomGoodsCode(int len) {
        return randomProductCode(Constants.GOODS_CODE_PREFIX, len);
    }

    public static String randomGoodsCode() {
        return randomProductCode(Constants.GOOD_CODE_LEN);
    }

    public static String uuid() {
        String authSid = UUID.randomUUID().toString() + randomAlphanumeric(32);
        return CryptUtils.sha256(authSid);
    }

    public static String traceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String uuid32() {
        return CryptUtils.md5(uuid());
    }

    public static String randomAuthSid(String prefix) {
        String authSid = UUID.randomUUID().toString() + randomAlphanumeric(32);
        if (StrUtils.isNotBlank(prefix)) {
            authSid = prefix.trim() + "::" + authSid;
        }
        return CryptUtils.sha256(authSid);
    }

    public static String sqlIn(List<String> list) {
        if (isEmpty(list)) {
            return "''";
        }
        return "'" + StringUtils.join(list, "','") + "'";
    }

    public static String sqlIn(Set<String> list) {
        if (isEmpty(list)) {
            return "''";
        }
        return "'" + String.join("','", list) + "'";
    }

    public static String valueInSql(List<Long> list) {
        if (isEmpty(list)) {
            return "''";
        }
        return "'" + StringUtils.join(list, "','") + "'";
    }

    public static String intInSql(List<Integer> list) {
        if (isEmpty(list)) {
            return "''";
        }
        return "'" + StringUtils.join(list, "','") + "'";
    }

    public static AreaVo areaInfo(String area) {
        if (StrUtils.isBlank(area)) {
            return null;
        }
        AreaVo vo = new AreaVo();
        vo.setArea(area);
        String[] areaArr = StrUtils.split(area, "||");
        int i = 0;
        Long regionId = Constants.LONG_ZERO;
        for (String val : areaArr) {
            i++;
            if (i > 5) {
                break;
            }
            String[] arr = StrUtils.split(val, "::");
            String name = arr[0].trim();
            Long id = Constants.LONG_ZERO;
            if (arr.length > 1) {
                id = toLong(arr[1]);
            }
            if (id != null && id.compareTo(Constants.LONG_ZERO) < 1) {
                continue;
            }
            regionId = id;
            switch (i) {
                case 1 -> {
                    vo.setProvince(id);
                    vo.setProvinceName(name);
                }
                case 2 -> {
                    vo.setCity(id);
                    vo.setCityName(name);
                }
                case 3 -> {
                    vo.setDistrict(id);
                    vo.setDistrictName(name);
                }
                case 4 -> {
                    vo.setStreet(id);
                    vo.setStreetName(name);
                }
                case 5 -> {
                    vo.setCommunity(id);
                    vo.setCommunityName(name);
                }
            }
        }
        vo.setRegionId(regionId);
        return vo;
    }

    public static Long getRegionId(String area) {
        AreaVo areaVo = areaInfo(area);
        if (ObjectUtils.isEmpty(areaVo)) {
            return null;
        }
        if (isID(areaVo.getRegionId())) {
            return areaVo.getRegionId();
        }
        if (isID(areaVo.getCommunity())) {
            return areaVo.getCommunity();
        }
        if (isID(areaVo.getStreet())) {
            return areaVo.getStreet();
        }
        if (isID(areaVo.getDistrict())) {
            return areaVo.getDistrict();
        }
        if (isID(areaVo.getCity())) {
            return areaVo.getCity();
        }

        return areaVo.getProvince();
    }

    public static Long getProvinceId(String area) {
        AreaVo areaVo = areaInfo(area);
        if (ObjectUtils.isEmpty(areaVo)) {
            return null;
        }
        return areaVo.getProvince();
    }

    public static Long getCityId(String area) {
        AreaVo areaVo = areaInfo(area);
        if (ObjectUtils.isEmpty(areaVo)) {
            return null;
        }
        return areaVo.getCity();
    }

    public static Long getDistrictId(String area) {
        AreaVo areaVo = areaInfo(area);
        if (ObjectUtils.isEmpty(areaVo)) {
            return null;
        }
        return areaVo.getDistrict();
    }

    public static Long getStreetId(String area) {
        AreaVo areaVo = areaInfo(area);
        if (ObjectUtils.isEmpty(areaVo)) {
            return null;
        }
        return areaVo.getStreet();
    }

    public static Long getCommunityId(String area) {
        AreaVo areaVo = areaInfo(area);
        if (ObjectUtils.isEmpty(areaVo)) {
            return null;
        }
        return areaVo.getCommunity();
    }

    public static OrgKindVo kindInfo(String kind) {
        if (StrUtils.isBlank(kind)) {
            return null;
        }
        OrgKindVo vo = new OrgKindVo();
        vo.setKind(kind);
        String[] arr = StrUtils.split(kind, "||");
        int i = 0;
        int len = arr.length;
        for (String val : arr) {
            i++;
            String[] tmpArr = StrUtils.split(val, "::");
            String name = tmpArr[0].trim();
            String code = null;
            if (arr.length > 1) {
                code = arr[1].trim();
            }
            if (StrUtils.isBlank(code)) {
                continue;
            }

            switch (i) {
                case 1 -> {
                    vo.setFirstCode(code);
                    vo.setFirstName(name);
                }
                case 2 -> {
                    vo.setSecondCode(code);
                    vo.setSecondName(name);
                }
                case 3 -> {
                    vo.setThirdCode(code);
                    vo.setThirdName(name);
                }
            }

            if (i == len) {
                vo.setKindCode(code);
                vo.setKindName(name);
                break;
            }
        }
        return vo;
    }

    public static String tableName(String name) {
        if (StrUtils.isBlank(name)) {
            return "";
        }
        String tableName = StrUtils.replace(name.trim(), "`", "");
        String[] arr = StrUtils.split(tableName, ".");
        if (arr.length < 2) {
            return tableName.trim();
        }
        return arr[arr.length - 1];
    }

    public static Set<String> getTables(Statement statement) {
        if (statement == null) {
            return null;
        }
        TablesNamesFinder<?> tablesNamesFinder = new TablesNamesFinder<>();
        return tablesNamesFinder.getTables(statement);
    }

    public static String concatValueString(String... strings) {
        if (ArrayUtils.isEmpty(strings)) {
            return null;
        }
        return StrUtils.join(strings, Constants.TEXT_SPLIT);
    }

    public static String cacheKeyName(
            String module,
            String object,
            String name
    ) {
        return module + "::" + object + "::" + name;
    }

    public static String cacheKeyName(
            String object,
            String name
    ) {
        return Constants.DEFAULT_MODULE + "::" + object + "::" + name;
    }

    public static String cacheKeyName(
            String name
    ) {
        return Constants.DEFAULT_MODULE + "::" + Constants.DEFAULT_OBJECT + "::" + name;
    }

    /**
     * 隐藏尾部字符串
     *
     * @param data
     * @param hideLen
     * @return
     */
    public static String strHideTail(String data, int hideLen) {
        if (data == null) {
            return null;
        }
        if (StrUtils.isBlank(data)) {
            return "";
        }
        int len = data.length();
        if (len < hideLen) {
            hideLen = len - 2;
        }
        if (hideLen < 1) {
            return DesensitizedUtil.firstMask(data);
        }
        return StrUtil.hide(data, len - hideLen, len);
    }

    public static StackTraceElement[] getTraceElement() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements.length < 3) {
            return null;
        }
        return ArrayUtils.subarray(stackTraceElements, 2, stackTraceElements.length);
    }

    /**
     * 隐藏中间字符串
     *
     * @param data
     * @param hideLen
     * @return
     */
    public static String strHideMiddle(String data, int hideLen) {
        if (data == null) {
            return null;
        }
        if (StrUtils.isBlank(data)) {
            return "";
        }
        int len = data.length();
        if (len < hideLen) {
            hideLen = len - 2;
        }
        if (hideLen < 1) {
            return DesensitizedUtil.firstMask(data);
        }
        if (hideLen > len - 2) {
            return StrUtil.hide(data, len - hideLen, len);
        }

        int start = (len / 2) - (hideLen / 2);
        int end = start + hideLen;

        return StrUtil.hide(data, start, end);
    }

    public static String getAuthCode(int count) {
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            int num = r.nextInt(str.length());
            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num) + ""), "");
        }
        return sb.toString();
    }

    public static boolean isTrue(Boolean value) {
        if (value == null) {
            return false;
        }
        return value.equals(Boolean.TRUE);
    }

    public static boolean isFalse(Boolean value) {
        if (value == null) {
            return true;
        }
        return !value.equals(Boolean.TRUE);
    }

    public static boolean isTrue(ActionResult result) {
        if (result == null) {
            return false;
        }
        return isTrue(result.getResult());
    }

    public static boolean isFalse(ActionResult result) {
        if (result == null) {
            return true;
        }
        return isFalse(result.getResult());
    }

    public static boolean isNum(String fields) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher number = pattern.matcher(fields);
        if (!number.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isNotNum(String fields) {
        return !isNum(fields);
    }

    public static String idNumber(String idNumber) {
        if (StrUtils.isBlank(idNumber)) {
            return idNumber;
        }

        return idNumber.replaceAll(
                "[^0-9Xx]"
                , ""
        );
    }

    public static String dictName(String str) {
        if (StrUtils.isBlank(str)) {
            return "";
        }
        String[] tmp = StringUtils.split(str.trim(), Constants.TEXT_SPLIT);
        return ArrayUtils.get(tmp, 0);
    }

    public static String dictValue(String str) {
        if (StrUtils.isBlank(str)) {
            return "";
        }
        String[] tmp = StringUtils.split(str.trim(), Constants.TEXT_SPLIT);
        return ArrayUtils.get(tmp, 1);
    }

    public static void main(String[] args) {
        String str = "abc";
        List<String> list = str
                .chars()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toList());
        log.info(
                "arr: {}",
                JsonUtil.toJSONString(list)
        );
    }
}
