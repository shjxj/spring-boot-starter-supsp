package com.supsp.springboot.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
@Slf4j
public class RsaUtil {
    public static String sign(String data, String privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(getPrivateKey(privateKey));
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    private static PrivateKey getPrivateKey(String privateKeyFilePath) throws Exception {
        try {
            // 从文件中读取私钥
            byte[] keyBytes = Files.readAllBytes(Paths.get(privateKeyFilePath));
            String base64String = new String(keyBytes);

            // 移除 PEM 格式的开头和结尾文本
            base64String = base64String.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replace("\r\n", "")
                    .replace("\n", "");

            byte[] decodedKeyBytes = Base64.getDecoder().decode(base64String);

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        } catch (IOException e) {
            throw new IOException("无法读取私钥文件: " + privateKeyFilePath, e);
        } catch (Exception e) {
            throw new Exception("生成私钥时发生错误", e);
        }
    }
}
