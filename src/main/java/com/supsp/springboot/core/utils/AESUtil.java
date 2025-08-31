package com.supsp.springboot.core.utils;

import com.supsp.springboot.core.config.CoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


@Component
@Slf4j
public class AESUtil {

    public static final String AESCBC_NO_PADDING = "AES/CBC/NoPadding";

    // private static CoreProperties coreProperties;

    // public AESUtil(CoreProperties coreProperties) {
    //     AESUtil.coreProperties = coreProperties;
    // }

    //编码
    private static final String ENCODING = "UTF-8";
    // 算法定义
    private static final String AES_ALGORITHM = "AES";
    // 指定填充方式
    private static final String CIPHER_MODEL_PADDING = "AES/ECB/PKCS5Padding";
    private static final String CIPHER_CBC_PADDING = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     *
     * @param content
     * @param aesKey
     * @param iv
     * @return
     */
    public static String encryptCBC(String content, String aesKey, String iv) {
        if (StrUtils.isBlank(content)) {
            return "";
        }

        if (StrUtils.isBlank(aesKey)) {
            throw new RuntimeException("aesKey is empty");
        }

        if (StrUtils.isBlank(iv)) {
            throw new RuntimeException("iv is empty");
        }

        try {
            String codeKey = encodeKey(aesKey);
            String ivStr = encodeKey(iv);
            //对密码进行编码
            byte[] bytes = codeKey.getBytes(ENCODING);
            //设置加密算法，生成秘钥
            SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance(CIPHER_CBC_PADDING);
            //偏移
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivStr.getBytes(ENCODING));
            //选择加密
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
            //根据待加密内容生成字节数组
            byte[] encrypted = cipher.doFinal(content.getBytes(ENCODING));
            //返回base64字符串
            return Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     *
     * @param content
     * @param aesKey
     * @param iv
     * @return
     */
    public static String decryptCBC(String content, String aesKey, String iv) {
        if (StrUtils.isBlank(content)) {
            return "";
        }

        if (StrUtils.isBlank(aesKey)) {
            throw new RuntimeException("aesKey is empty");
        }

        if (StrUtils.isBlank(iv)) {
            throw new RuntimeException("iv is empty");
        }

        try {
            String codeKey = encodeKey(aesKey);
            String ivStr = encodeKey(iv);

            //对密码进行编码
            byte[] bytes = codeKey.getBytes(ENCODING);
            //设置解密算法，生成秘钥
            SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
            //偏移
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivStr.getBytes(ENCODING));
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance(CIPHER_CBC_PADDING);
            //选择解密
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);

            //先进行Base64解码
            byte[] decodeBase64 = Base64.decodeBase64(content);

            //根据待解密内容进行解密
            byte[] decrypted = cipher.doFinal(decodeBase64);
            //将字节数组转成字符串
            return new String(decrypted, ENCODING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密
     *
     * @param content
     * @param aesKey
     * @return
     * @throws RuntimeException
     */
    public static String encrypt(String content, String aesKey) throws RuntimeException {
        if (StrUtils.isBlank(content)) {
            return "";
        }

        if (StrUtils.isBlank(aesKey)) {
            throw new RuntimeException("aesKey is empty");
        }

        try {
            String codeKey = encodeKey(aesKey);
            byte[] bytes = codeKey.getBytes(ENCODING);
            SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_MODEL_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes(ENCODING));
            return encode(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String content) throws RuntimeException {
        return encrypt(content, CoreProperties.ENCODE_KEY);
    }

    /**
     * 解密
     *
     * @param content
     * @param aesKey
     * @return
     */
    public static String decrypt(String content, String aesKey) {
        if (StrUtils.isBlank(content)) {
            return "";
        }

        if (StrUtils.isBlank(aesKey)) {
            throw new RuntimeException("aesKey is empty");
        }

        try {
            String codeKey = encodeKey(aesKey);
            byte[] bytes = codeKey.getBytes(ENCODING);
            SecretKeySpec skeySpec = new SecretKeySpec(bytes, AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_MODEL_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] decodeBase64 = decode(content);
            byte[] decrypted = cipher.doFinal(decodeBase64);
            return new String(decrypted, ENCODING);
        } catch (Exception e) {
            //throw new RuntimeException(e);
        }
        return content;
    }

    public static String decrypt(String content) throws RuntimeException {
        return decrypt(content, CoreProperties.ENCODE_KEY);
    }

    public static String encodeKey(String key) {
        if (StrUtils.isBlank(key)) {
            return "";
        }
        String str = DigestUtils.md5Hex(key);
        return StrUtils.substring(str, 0, 8) + StrUtils.substring(str, -8);
    }

    /**
     * 编码
     *
     * @param byteArray
     * @return
     */
    public static String encode(byte[] byteArray) {
        return Base64.encodeBase64String(byteArray).trim();
    }

    /**
     * 解码
     *
     * @param base64EncodedString
     * @return
     */
    public static byte[] decode(String base64EncodedString) {
        return Base64.decodeBase64(base64EncodedString.trim());
    }

}
