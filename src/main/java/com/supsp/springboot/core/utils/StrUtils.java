package com.supsp.springboot.core.utils;


import org.apache.commons.lang3.StringUtils;

public class StrUtils {

    public static boolean isAnyBlank(CharSequence... css) {
        return StringUtils.isAnyBlank(css);
    }

    public static boolean isNoneBlank(String str) {
        return StringUtils.isNoneBlank(str);
    }

    @SafeVarargs
    public static <T extends CharSequence> T firstNonBlank(T... values) {
        return StringUtils.firstNonBlank(values);
    }

    public static boolean isAnyEmpty(CharSequence... css) {
        return StringUtils.isAnyEmpty(css);
    }

    public static boolean isNoneEmpty(String str) {
        return StringUtils.isNoneEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return StringUtils.isNotEmpty(str);
    }

    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str);
    }

    public static String join(Object[] array, String delimiter) {
        return StringUtils.join(
                array,
                delimiter
        );
    }

    public static String join(Iterable<?> iterable, String separator) {
        return StringUtils.join(
                iterable,
                separator
        );
    }

    public static String[] split(String str, String separatorChars) {
        return StringUtils.split(str, separatorChars);
    }

    public static boolean contains(CharSequence seq, CharSequence searchSeq) {
        return StringUtils.contains(
                seq,
                searchSeq
        );
    }

    public static boolean containsIgnoreCase(CharSequence str, CharSequence searchStr) {
        return StringUtils.containsIgnoreCase(
                str,
                searchStr
        );
    }

    public static boolean startsWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return StringUtils.startsWithIgnoreCase(
                str,
                prefix
        );
    }

    public static boolean endsWithIgnoreCase(CharSequence str, CharSequence suffix) {
        return StringUtils.endsWithIgnoreCase(
                str,
                suffix
        );
    }

    public static String replace(String text, String searchString, String replacement) {
        return StringUtils.replace(text, searchString, replacement);
    }

    public static String leftPad(String str, int size, char padChar) {
        return StringUtils.leftPad(str, size, padChar);
    }

    public static String leftPad(String str, int size, String padStr) {
        return StringUtils.leftPad(str, size, padStr);
    }

    public static String rightPad(String str, int size, char padChar) {
        return StringUtils.rightPad(str, size, padChar);
    }

    public static String rightPad(String str, int size, String padStr) {
        return StringUtils.rightPad(str, size, padStr);
    }

    public static String removeStartIgnoreCase(String str, String remove) {
        return StringUtils.removeStartIgnoreCase(
                str,
                remove
        );
    }

    public static String removeEndIgnoreCase(String str, String remove) {
        return StringUtils.removeEndIgnoreCase(
                str,
                remove
        );
    }

    public static String substring(String str, int start) {
        return StringUtils.substring(str, start);
    }

    public static String substring(String str, int start, int end) {
        return StringUtils.substring(str, start, end);
    }

    public static String[] splitByCharacterTypeCamelCase(String str) {
        return StringUtils.splitByCharacterTypeCamelCase(str);
    }

}
