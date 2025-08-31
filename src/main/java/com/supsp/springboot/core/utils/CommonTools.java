package com.supsp.springboot.core.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.supsp.springboot.core.base.ActionResult;
import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.consts.Constants;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CommonTools {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    public static String[] capitalizeWords(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        String[] words = input.split(" ");
        String[] result = ArrayUtils.newInstance(String.class, words.length);

        short i = 0;
        for (String word : words) {
            if (!word.isEmpty()) {
                result[i] = Character.toUpperCase(word.charAt(0)) + word.substring(1);
                i++;
            }
        }

        return result;
    }

    public static String unCapitalizeWords(String input) {
        if (StrUtils.isBlank(input)) {
            return null;
        }
        return input.replaceAll("[A-Z]", " $0").trim();
    }

    public static String[] unCapitalizeToWords(String input) {
        String str = unCapitalizeWords(input);
        if (StrUtils.isBlank(str)) {
            return null;
        }
        return str.toLowerCase().trim().split(" ");
    }

    public static String capitalizeTableName(String name, String module) {
        if (StrUtils.isBlank(name)) {
            return null;
        }
        String tableName = name.trim().toLowerCase();
        if (
                StrUtils.isNotBlank(module)
                        && tableName.toLowerCase().startsWith(module.trim().toLowerCase() + "_")
        ) {
            tableName = StrUtils.substring(tableName, module.trim().length() + 1);
//  tableName.substring(module.trim().length() + 1)
        }
        // log.debug("tableName: {};", tableName);

        String[] words = capitalizeWords(tableName.toLowerCase().replace("_", " "));

        return StrUtils.join(words, "");
    }

    public static String capitalizeTableName(String name) {
        if (StrUtils.isBlank(name)) {
            return null;
        }

        String[] words = capitalizeWords(name.toLowerCase().replace("_", " "));

        return StrUtils.join(words, "");
    }

    public static String mobileAccountPwd(String mobile) {
        if (StrUtils.isBlank(mobile)) {
            return null;
        }
        return StrUtils.substring(mobile, -6) + "@!!";
        // return CryptUtils.hashpw(pwd);
    }

    public static JavaTimeModule timeModule() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(
                LocalDate.class,
                new LocalDateSerializer(dateFormatter)
        );
        timeModule.addSerializer(
                LocalTime.class,
                new LocalTimeSerializer(timeFormatter)
        );
        timeModule.addSerializer(
                LocalDateTime.class,
                new LocalDateTimeSerializer(dateTimeFormatter)
        );

        timeModule.addDeserializer(
                LocalDate.class,
                new LocalDateDeserializer(dateFormatter)
        );
        timeModule.addDeserializer(
                LocalTime.class,
                new LocalTimeDeserializer(timeFormatter)
        );
        timeModule.addDeserializer(
                LocalDateTime.class,
                new LocalDateTimeDeserializer(dateTimeFormatter)
        );
        return timeModule;
    }

    public static boolean validActionResult(ActionResult result) {
        return (
                ObjectUtils.isNotEmpty(result)
                        && CommonUtils.isTrue(result.getResult())
        );
    }

    public static String formatJson(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    /**
     * 打印当前线程的调用堆栈
     */
    public static void printTrack() {
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        StringBuilder sbf = new StringBuilder();
        int i = 0;
        for (StackTraceElement e : st) {
            i++;
            if (i < 1 || i > 20) {
                continue;
            }
            if (!sbf.isEmpty()) {
                //sbf.append(" <- ");
                sbf.append(System.lineSeparator());
            }
            sbf.append(
                    String.format(
                            // "%d: \033[36;4m%s\033[0m -> \033[35m%s()\033[0m",
                            "%d: %s %s",
                            i,
                            e.toString(),
                            e.getMethodName()
                    )
            );
//            sbf.append(java.text.MessageFormat.format("{0}.{1}() {2}"
//                    , e.getClassName()
//                    , e.getMethodName()
//                    , e.getLineNumber()));
        }
        System.out.println(sbf.toString());
    }

    public static boolean isEnv(String env) {
        return StrUtils.isNotBlank(env) && env.equals(CoreProperties.PROFILE_ACTIVE);
    }

    public static boolean isDev() {
        return Constants.DEV_ENVS.contains(CoreProperties.PROFILE_ACTIVE);
    }

    public static boolean isEnvLocal() {
        return Constants.STRING_LOCAL.equals(CoreProperties.PROFILE_ACTIVE);
    }

    public static boolean isEnvDockerLocal() {
        return Constants.STRING_DOCKER_LOCAL.equals(CoreProperties.PROFILE_ACTIVE);
    }

    public static boolean isEnvWlan() {
        return Constants.STRING_WLAN.equals(CoreProperties.PROFILE_ACTIVE);
    }

    public static boolean isEnvDev() {
        return Constants.STRING_DEV.equals(CoreProperties.PROFILE_ACTIVE);
    }

    public static boolean isEnvText() {
        return Constants.STRING_TEST.equals(CoreProperties.PROFILE_ACTIVE);
    }

    public static boolean isEnvProduct() {
        return Constants.STRING_MASTER.equals(CoreProperties.PROFILE_ACTIVE);
    }

    public static boolean isJSONString(String content) {
        return JSONUtil.isTypeJSON(content);
    }

    public static void setHeader(
            HttpServletResponse response,
            String name,
            String value
    ) {
//        if (
//                response == null
//                        || StrUtils.isBlank(name)
//                        || AuthCommon.isMq()
//        ) {
//            return;
//        }
        response.setHeader(
                name,
                value
        );
    }

    public static String batchSeqName(
            Integer seq,
            String batchName,
            String prefix,
            String suffix
    ) {
        if (seq == null || NumUtils.leZero(seq)) {
            return null;
        }
        String name = String.valueOf(seq);
        if (StrUtils.isNotBlank(batchName)) {
            name = batchName + name;
        }
        if (StrUtils.isNotBlank(prefix)) {
            name = prefix + name;
        }
        if (StrUtils.isNotBlank(suffix)) {
            name = name + suffix;
        }
        return name;
    }


    public static String[] splitCapitalize(String val) {
        if (StrUtils.isBlank(val)) {
            return null;
        }
        return ArrayUtil.removeEmpty(
                val.trim().replaceAll("[A-Z]", "_$0").split("_")
        );
    }

    public static String removeCglib(String val) {
        if (StrUtils.isBlank(val)) {
            return val;
        }
        if (
                !StringUtils.containsIgnoreCase(
                        val.trim(),
                        Constants.CGLIB_CLASS_SEPARATOR
                )
        ) {
            return val;
        }
        String[] temp = val.trim().split(Constants.REGEX_CGLIB_CLASS_SEPARATOR);
        return temp[0].trim();
    }

    public static <T> T getKey(Map map, Object value) {
        for (Object key : map.keySet()) {
            if (map.get(key).equals(value)) {
                return (T) key;
            }
        }
        return null;
    }


    public static String numToLetter(int number) {
        byte[] b = (String.valueOf(number)).getBytes();
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) (b[i] + 49);
        }
        return (new String(b)).toUpperCase();
    }

    public static List<String> splitString(String str) {
        if (StrUtils.isBlank(str)) {
            return null;
        }
        char[] chars = str.trim().toCharArray();
        List<String> res = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            String val = String.valueOf(chars[i]);
            if (StrUtils.isNotBlank(val)) {
                res.add(val);
            }
        }
        return res;
    }

    //
    public static String moneyString(
            BigDecimal money,
            String defaultValue
    ) {
        if (money == null) {
            if (StrUtils.isNotBlank(defaultValue)) {
                return defaultValue;
            }
            return "¥ 0.00 元";
        }
        DecimalFormat dfm = new DecimalFormat("#0.00");
        return "¥ " + dfm.format(money) + " 元";
    }

    public static void main(String[] args) {
        String answer = "AbBCDDE";
        List<String> answerList = splitString(answer);
        List<String> list = List.of(
                "b",
                "A",
                "d",
                "E",
                "F",
                "c"
        );

        List<String> reList = list.stream()
                .map(v -> v.trim().toUpperCase())
                .toList();
        List<String> reAnswer = answerList.stream()
                .map(v -> v.trim().toUpperCase())
                .toList();

        log.info(
                """
                        answerList: {};
                        list: {};
                        reList: {};
                        reAnswer: {};
                        all: {},
                        """,
                JsonUtil.toJSONString(answerList),
                JsonUtil.toJSONString(list),
                JsonUtil.toJSONString(reList),
                JsonUtil.toJSONString(reAnswer),
                CollectionUtils.containsAll(
                        reList,
                        reAnswer
                )
        );
    }
}
