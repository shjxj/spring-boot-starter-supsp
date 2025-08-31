package com.supsp.springboot.core.utils;


import cn.hutool.crypto.digest.BCrypt;
import com.supsp.springboot.core.config.CoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

@Slf4j
public class CryptUtils {

    /**
     * 编码 格式
     */
    private static String encoding = "UTF-8";

    /**
     * 算法
     */
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    private static CoreProperties coreProperties;

    public CryptUtils(CoreProperties coreProperties) {
        CryptUtils.coreProperties = coreProperties;
    }

    public static String encrypt(String data, String key, String iv) {
        return AESUtil.encryptCBC(data, key, iv);
    }

    public static String decrypt(String data, String key, String iv) {
        return AESUtil.decryptCBC(data, key, iv);
    }

    public static String encrypt(String data, String key) {
        return encrypt(data, key, CoreProperties.ENCODE_IV);
    }

    public static String decrypt(String data, String key) {
        return decrypt(data, key, CoreProperties.ENCODE_IV);
    }

    public static String encrypt(String data) {
        return encrypt(data, CoreProperties.ENCODE_KEY, CoreProperties.ENCODE_IV);
    }

    public static String decrypt(String data) {
        return decrypt(data, CoreProperties.ENCODE_KEY, CoreProperties.ENCODE_IV);
    }

    public static String hashpw(String password) {
        if (StrUtils.isBlank(password)) {
            return "";
        }
        return BCrypt.hashpw(password);
    }

    public static boolean checkpw(String data, String hashPassword) {
        return BCrypt.checkpw(data, hashPassword);
    }

    public static String md5(String data) {
        if (data == null) {
            return null;
        }
        return DigestUtils.md5Hex(data);
    }

    public static String md5(String data, String salt) {
        if (data == null) {
            return null;
        }
        return md5(data + "||" + ((salt == null) ? "" : salt));
    }

    public static String sha1(String data) {
        if (data == null) {
            return null;
        }
        return DigestUtils.sha1Hex(data);
    }

    public static String sha1(String data, String salt) {
        if (data == null) {
            return null;
        }
        return sha1(data + "||" + ((salt == null) ? "" : salt));
    }

    public static String sha256(String data) {
        if (data == null) {
            return null;
        }
        return DigestUtils.sha256Hex(data);
    }

    public static String sha256(String data, String salt) {
        if (data == null) {
            return null;
        }
        return sha256(data + "||" + ((salt == null) ? "" : salt));
    }

}
