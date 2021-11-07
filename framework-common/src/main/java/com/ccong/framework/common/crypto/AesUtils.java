package com.ccong.framework.common.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Deprecated
public class AesUtils {

    private static final Logger log = LoggerFactory.getLogger(AesUtils.class);

    /**
     * AES加密
     * @param text 待解密的文本
     * @param key  密钥：128bit=AES128，也就是16个英文字母；当它增加到32个英文字母是，就是256bit时，就是AES256
     * @param salt 盐值
     * @return 解密结果；如果解密失败，返回null
     */
    public static String encryptByCfb(String key, String salt, String text) {
        try {
            IvParameterSpec iv = new IvParameterSpec(salt.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CFB/NOPADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(text.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("text encrypt exception: {}", text, e);
        }

        return null;
    }

    /**
     * AES解密
     *
     * @param text 待解密的文本
     * @param key  密钥：128bit=AES128，也就是16个英文字母；当它增加到32个英文字母是，就是256bit时，就是AES256
     * @param salt 盐值
     * @return 解密结果；如果解密失败，返回null
     */
    public static String decryptByCfb(String text, String key, String salt) {
        String result;
        try {
            byte[] bytes = Base64.getDecoder().decode(text);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec paramSpec = new IvParameterSpec(salt.getBytes());
            Cipher ecipher;
            ecipher = Cipher.getInstance("AES/CFB/NOPADDING");
            ecipher.init(Cipher.DECRYPT_MODE, secretKeySpec, paramSpec);
            byte[] messages = ecipher.doFinal(bytes);
            result = new String(messages, "UTF-8");
        } catch (Exception e) {
            result = null;
            log.error("text decrypt exception: {}", text, e);

        }
        return result;
    }
}
