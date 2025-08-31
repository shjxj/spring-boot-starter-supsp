package com.supsp.springboot.core.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author zwn
 * @description 日期时间转换工具类
 * @date 2023年10月16日} 11:48
 */
@Slf4j
public class DateUtils {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SHORT_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_PATTERN_CN = "yyyy年MM月dd日";
    public static final String DATE_PATTERN_MONTH_CN = "yyyy年MM月";
    public static final String TIME_PATTERN = "HH:mm:ss";

    public static final String DATE_START_TIME_PATTERN = "yyyy-MM-dd 00:00:00";
    public static final String DATE_END_TIME_PATTERN = "yyyy-MM-dd 23:59:59";

    /**
     * @Description: //获取当天的开始时间
     * @DateTime: 2024-11-27 13:48
     * @Param:
     * @Return: null
     */
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * @Description: //获取当天的结束时间
     * @DateTime: 2024-11-27 13:48
     * @Param:
     * @Return: null
     */
    public static Date getDayEnd() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date toDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime getDateTime(String datetime, String pattern) {
        if (StrUtils.isBlank(datetime)) {
            return null;
        }
        if (StrUtils.isBlank(pattern)) {
            pattern = DATE_TIME_PATTERN;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(
                datetime,
                DateTimeFormatter.ofPattern(pattern)
        );
    }

    public static LocalDateTime getDateTime(String datetime) {
        return getDateTime(
                datetime,
                DATE_TIME_PATTERN
        );
    }

    public static LocalDateTime getDateTime(Date date) {
        return LocalDateTimeUtil.of(date);
    }

    /**
     * Integer转时间戳 默认yyyy-MM-dd HH:mm:ss
     */
    public static Long toTimestamp(String time, String pattern) {
        if (StrUtils.isBlank(pattern)) {
            pattern = DATE_TIME_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date.getTime() / 1000L;
    }

    public static Long toTimestamp(String time) {
        return toTimestamp(time, DATE_TIME_PATTERN);
    }

    public static Long toTimestamp(LocalDateTime time) {
        if (ObjectUtils.isEmpty(time)) {
            return null;
        }
        try {
            return time.toEpochSecond(OffsetDateTime.now().getOffset());
        } catch (Exception e) {
            return null;
        }
    }

    public static Long toTimestamp(LocalDate date) {
        if (ObjectUtils.isEmpty(date)) {
            return null;
        }
        try {
            return toTimestamp(
                    LocalDateTime.of(
                            date.getYear(),
                            date.getMonth(),
                            date.getDayOfMonth(),
                            0,
                            0
                    )
            );
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 时间戳转字符串  默认yyyy-MM-dd HH:mm:ss
     */
    public static String toDate(long timestamp, String pattern) {
        if (StrUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        Instant instant = Instant.ofEpochMilli(timestamp * 1000L);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    public static String toDate(long unixTimestamp) {
        return toDate(unixTimestamp, DATE_TIME_PATTERN);
    }

    public static String format(LocalDateTime localDateTime, String pattern) {
        if (localDateTime == null) {
            return null;
        }
        return DateUtil.format(localDateTime, pattern);
    }

    public static String format(long unixTimestamp, String pattern) {
        LocalDateTime dateTime = toLocalDateTime(unixTimestamp);
        if (ObjectUtils.isEmpty(dateTime)) {
            return null;
        }
        return DateUtil.format(dateTime, pattern);
    }

    public static String format(LocalDate localDate, String pattern) {
        if (localDate == null) {
            return null;
        }
        return DateUtil.format(localDate.atStartOfDay(), pattern);
    }

    public static String toDate(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime, pattern);
    }

    public static String toDate(LocalDateTime localDateTime) {
        return toDate(localDateTime, DATE_PATTERN);
    }

    public static String toTime(LocalDateTime localDateTime, String pattern) {
        return format(localDateTime, pattern);
    }

    public static String toTime(LocalDateTime localDateTime) {
        return toTime(localDateTime, TIME_PATTERN);
    }

    public static LocalDateTime toLocalDateTime(long timestamp, String pattern) {
        if (timestamp < 1) {
            return null;
        }
        if (StrUtils.isBlank(pattern)) {
            pattern = DATE_TIME_PATTERN;
        }
        Instant instant = Instant.ofEpochMilli(timestamp * 1000L);
        try {
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDateTime toLocalDateTime(long unixTimestamp) {
        return toLocalDateTime(unixTimestamp, DATE_TIME_PATTERN);
    }

    public static LocalDateTime toLocalDateTime(String datetime, String format) {
        if (StrUtils.isBlank(datetime)) {
            return null;
        }
        if (StrUtils.isBlank(format)) {
            format = DATE_TIME_PATTERN;
        }
        try {
            return LocalDateTime.parse(
                    datetime,
                    DateTimeFormatter.ofPattern(format)
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static LocalDateTime toLocalDateTime(String datetime) {
        return toLocalDateTime(datetime, DATE_TIME_PATTERN);
    }

    public static LocalDate toLocalDate(Long timestamp) {
        if (NumUtils.leZero(timestamp)) {
            return null;
        }
        Instant instant = Instant.ofEpochSecond(timestamp);
        try {
            return LocalDate.ofInstant(instant, ZoneId.systemDefault());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取周数
     */
    public static int getWeek(String str) {
        Date date = DateUtil.parse(str);
        if (ObjectUtils.isEmpty(date)) {
            return 0;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);

        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static String dateTimeFormat(LocalDateTime dateTime) {
        return format(dateTime, DATE_TIME_PATTERN);
    }

    public static LocalDateTime parseDatetime(
            String date,
            String time
    ) {
        if (StrUtils.isBlank(date) || StrUtils.isBlank(time)) {
            return null;
        }
        try {
            return LocalDateTimeUtil.parse(date + "T" + time);
        } catch (Exception e) {
            //log
        }
        return null;
    }

    public static LocalDateTime parseDatetime(
            String date
    ) {
        return parseDatetime(date, "00:00:00");
    }

    public static Duration between(
            LocalDateTime start,
            LocalDateTime end
    ) {
        if (ObjectUtils.isEmpty(start) || ObjectUtils.isEmpty(end)) {
            return null;
        }
        return LocalDateTimeUtil.between(start, end);
    }

    public static Duration betweenToNow(
            String date,
            String time
    ) {
        LocalDateTime end = parseDatetime(date, time);
        if (ObjectUtils.isEmpty(end)) {
            return null;
        }
        return LocalDateTimeUtil.between(LocalDateTime.now(), end);
    }

    public static Duration betweenToNow(
            String date
    ) {
        LocalDateTime end = parseDatetime(date);
        if (ObjectUtils.isEmpty(end)) {
            return null;
        }
        return LocalDateTimeUtil.between(LocalDateTime.now(), end);
    }

    public static Long toEndSeconds(
            String date,
            String time
    ) {
        Duration between = betweenToNow(date, time);
        if (ObjectUtils.isEmpty(between)) {
            return null;
        }
        return between.toSeconds();
    }

    public static Long toEndSeconds(
            String date
    ) {
        Duration between = betweenToNow(date);
        if (ObjectUtils.isEmpty(between)) {
            return null;
        }
        return between.toSeconds();
    }

    public static boolean isAfter(
            LocalDateTime dateTime
    ) {
        if (ObjectUtils.isEmpty(dateTime)) {
            return false;
        }
        return LocalDateTimeUtil.between(LocalDateTime.now(), dateTime).toSeconds() > 0;
    }

    public static boolean isAfter(
            String date,
            String time
    ) {
        Long seconds = toEndSeconds(date, time);
        if (ObjectUtils.isEmpty(seconds)) {
            return false;
        }
        return NumUtils.gtZero(seconds);
    }

    public static boolean isAfter(
            String date
    ) {
        Long seconds = toEndSeconds(date);
        if (ObjectUtils.isEmpty(seconds)) {
            return false;
        }
        return NumUtils.gtZero(seconds);
    }

    public static boolean isBefore(
            LocalDateTime dateTime
    ) {
        if (ObjectUtils.isEmpty(dateTime)) {
            return false;
        }
        return LocalDateTimeUtil.between(LocalDateTime.now(), dateTime).toSeconds() < 0;
    }

    public static boolean isBefore(
            String date,
            String time
    ) {
        Long seconds = toEndSeconds(date, time);
        if (ObjectUtils.isEmpty(seconds)) {
            return false;
        }
        return NumUtils.ltZero(seconds);
    }

    public static boolean isBefore(
            String date
    ) {
        Long seconds = toEndSeconds(date);
        if (ObjectUtils.isEmpty(seconds)) {
            return false;
        }
        return NumUtils.ltZero(seconds);
    }

    public static Integer dateYear(LocalDateTime dateTime) {
        return Integer.parseInt(DateUtil.format(dateTime, "yyyy"));
    }

    public static Integer dateYear() {
        return dateYear(LocalDateTime.now());
    }

    public static Short dateMonth(LocalDateTime dateTime) {
        return Short.parseShort(DateUtil.format(dateTime, "M"));
    }

    public static Short dateMonth() {
        return dateMonth(LocalDateTime.now());
    }

    public static Long dateYearMonth(LocalDateTime dateTime) {
        return Long.parseLong(DateUtil.format(dateTime, "yyyyMM"));
    }

    public static Long dateYearMonth() {
        return dateYearMonth(LocalDateTime.now());
    }

    public static LocalDateTime firstDayOfMonth() {
        LocalDate monthDate = LocalDate.now();
        YearMonth yearMonth = YearMonth.from(monthDate);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        return firstDayOfMonth.atStartOfDay();
    }

    public static LocalDateTime lastDayOfMonth() {
        LocalDate monthDate = LocalDate.now();
        YearMonth yearMonth = YearMonth.from(monthDate);
        LocalDate lastDayOfMonth = yearMonth.plusMonths(1).atDay(1).minusDays(1);
        return lastDayOfMonth.atStartOfDay();
    }

    /**
     * 获取两个日期之间的所有月份 (年月)
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<LocalDate> getMonthBetweenDate(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 声明保存日期集合
        List<LocalDate> list = new ArrayList<LocalDate>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime() <= endDate.getTime()) {
                list.add(toLocalDate(startDate));
                calendar.setTime(startDate);
                calendar.add(Calendar.MONTH, 1);
                startDate = calendar.getTime();
            }
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        return list;
    }

    public static List<LocalDate> getMonthBetweenDate(LocalDateTime startTime, LocalDateTime endTime) {
        if (ObjectUtils.isEmpty(startTime) || ObjectUtils.isEmpty(endTime)) {
            return null;
        }
        return getMonthBetweenDate(
                format(startTime, DATE_PATTERN),
                format(endTime, DATE_PATTERN)
        );
    }

    //判断选择的日期是否是本周
    public static boolean isThisWeek(Date time) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(time);
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }

    public static boolean isBeforeYear(LocalDateTime dateTime) {
        if (ObjectUtils.isEmpty(dateTime)) {
            return false;
        }
        return dateTime.getYear() < LocalDateTime.now().getYear();
    }

    public static boolean isThisYear(LocalDateTime dateTime) {
        if (ObjectUtils.isEmpty(dateTime)) {
            return false;
        }
        return dateTime.getYear() == LocalDateTime.now().getYear();
    }

    public static boolean isAfterYear(LocalDateTime dateTime) {
        if (ObjectUtils.isEmpty(dateTime)) {
            return false;
        }
        return dateTime.getYear() > LocalDateTime.now().getYear();
    }

    public static List<LocalDateTime> yearMonths(LocalDateTime dateTime) {
        if (ObjectUtils.isEmpty(dateTime)) {
            return null;
        }
        if (isAfterYear(dateTime)) {
            return null;
        }
        int year = dateTime.getYear();
        List<LocalDateTime> res = new ArrayList<>();
        int endMonth = LocalDateTime.now().getMonthValue();
        if (isBeforeYear(dateTime)) {
            endMonth = 12;
        }
        for (int i = 1; i <= endMonth; i++) {
            res.add(LocalDateTime.of(year, i, 1, 0, 0));
        }
        return res;
    }

    //
    public static LocalDateTime startTime(LocalDateTime dateTime) {
        if (ObjectUtils.isEmpty(dateTime)) {
            return null;
        }
        return LocalDateTime.of(
                dateTime.getYear(),
                dateTime.getMonth(),
                dateTime.getDayOfMonth(),
                0,
                0,
                0
        );
    }

    public static LocalDateTime endTime(LocalDateTime dateTime) {
        if (ObjectUtils.isEmpty(dateTime)) {
            return null;
        }
        return LocalDateTime.of(
                dateTime.getYear(),
                dateTime.getMonth(),
                dateTime.getDayOfMonth(),
                23,
                59,
                59
        );
    }

    public static LocalDateTime startTime(List<LocalDateTime> dateTimeRange) {
        if (
                dateTimeRange == null
                        || dateTimeRange.isEmpty()
                        || dateTimeRange.getFirst() == null
        ) {
            return null;
        }
        return startTime(dateTimeRange.getFirst());
    }

    public static LocalDateTime endTime(List<LocalDateTime> dateTimeRange) {
        if (
                dateTimeRange == null
                        || dateTimeRange.size() < 2
                        || dateTimeRange.get(1) == null
        ) {
            return null;
        }
        return endTime(dateTimeRange.get(1));
    }

    public static List<LocalDateTime> yearMonths(String dateTime) {
        return yearMonths(toLocalDateTime(dateTime));
    }

    public static LocalDateTime yearMonth(
            Integer detailYear,
            Short detailMonth
    ) {
        if (ObjectUtils.isEmpty(detailYear) || ObjectUtils.isEmpty(detailMonth)) {
            return null;
        }
        return LocalDateTime.of(
                detailYear,
                detailMonth.intValue(),
                1,
                0,
                0
        );
    }

    public static void main(String[] args) {
    }

}
