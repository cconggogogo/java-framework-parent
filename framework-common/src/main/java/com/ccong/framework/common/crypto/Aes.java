package com.ccong.framework.common.crypto;


import com.ccong.framework.common.exception.biz.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

/**
 * 通过AES算法对文本进行加密解密
 *
 */
public class Aes {

    private static final Logger log = LoggerFactory.getLogger(Aes.class);

    private final SecretKey key;                       //加密密钥
    private final AlgorithmParameterSpec paramSpec;    //算法参数
    private final Cipher ecipher;                      //加密算法

    public Aes(byte[] keyBytes, byte[] ivBytes) {
        KeyGenerator kgen;
        try {
            //为指定算法生成一个密钥生成器对象。
            kgen = KeyGenerator.getInstance("AES");
            //使用用户提供的随机源初始化此密钥生成器，使其具有确定的密钥长度。
            kgen.init(128, new SecureRandom(keyBytes));
            //使用KeyGenerator生成（对称）密钥。
            this.key = kgen.generateKey();
            //使用iv中的字节作为IV来构造一个 算法参数。
            this.paramSpec = new IvParameterSpec(ivBytes);
            //生成一个实现指定转换的 Cipher 对象
            this.ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e) {
            throw new BusinessException("构造AES CFB加密对象异常", e);
        }
    }


    /**
     * AES加密
     *
     * @param text 待解密的文本
     * @return 解密结果；如果解密失败，返回null
     */
    public String encrypt(String text) {
        if (text == null) {
            return null;
        }

        String str;
        try {
            //用密钥和一组算法参数初始化此 cipher
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            //加密并转换成16进制字符串
            str = asHex(ecipher.doFinal(text.getBytes()));
        } catch (Exception e) {
            log.error("text encrypt exception: {}", text, e);
            str = null;
        }
        return str;
    }

    /**
     * AES解密
     *
     * @param text 待解密的文本
     * @return 解密结果；如果解密失败，返回null
     */
    public String decrypt(String text) {
        if (text == null) {
            return null;
        }

        byte[] bytes = asBin(text);
        if (bytes == null) {
            return null;
        }

        String str;
        try {
            ecipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            str = new String(ecipher.doFinal(bytes));
        } catch (Exception e) {
            log.error("text decrypt exception: {}", text, e);
            str = null;
        }
        return str;
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param buf
     * @return
     */
    private static String asHex(byte buf[]) {
        StringBuilder strBuffer = new StringBuilder(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10)//小于十前面补零
            {
                strBuffer.append("0");
            }
            strBuffer.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strBuffer.toString();
    }

    /**
     * 将16进制字符串转换成字节数组
     *
     * @param src
     * @return
     */
    private static byte[] asBin(String src) {
        if (src.length() < 1) {
            return null;
        }
        byte[] encrypted = new byte[src.length() / 2];
        for (int i = 0; i < src.length() / 2; i++) {
            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);//取高位字节
            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);//取低位字节
            encrypted[i] = (byte) (high * 16 + low);
        }
        return encrypted;
    }
}
