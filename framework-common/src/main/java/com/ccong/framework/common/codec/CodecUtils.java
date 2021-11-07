package com.ccong.framework.common.codec;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import com.google.common.html.HtmlEscapers;
import com.google.common.io.BaseEncoding;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * 包含一些通用的编码解码算法，包括：
 * 1.MD5
 * 2.sha
 * 3.去掉html敏感字符
 * 4.base64
 */
public final class CodecUtils {

    private CodecUtils() {

    }

    /**
     * 求1个文件的32位MD指纹，不接受null值
     *
     * @param file 目标文件：不能为null，否则抛出空指针异常；如果读取失败或者文件不存在，将抛出IO异常
     * @return 该文件的MD5指纹【小写形式】
     * @throws IOException 当文件不存在或者读取失败时抛出
     */
    public static String md5(File file) throws IOException {
        Preconditions.checkNotNull(file);
        return Files.asByteSource(file).hash(Hashing.md5()).toString();
    }

    /**
     * 求1个文件的16位MD指纹，不接受null值
     *
     * @param file 目标文件：不能为null，否则抛出空指针异常；如果读取失败或者文件不存在，将抛出IO异常
     * @return 该文件的MD5指纹【小写形式】
     * @throws IOException 当文件不存在或者读取失败时抛出
     */
    public static String md5Short(File file) throws IOException {
        return md5(file).substring(8, 24);
    }

    /**
     * 求1个byte数组的32位MD指纹，不接受null值
     *
     * @param bytes 目标数组：不能为null，否则抛出空指针异常
     * @return 该文件的MD5指纹【小写形式】
     */
    public static String md5(byte[] bytes) {
        Preconditions.checkNotNull(bytes);
        return Hashing.md5().newHasher().putBytes(bytes).hash().toString();
    }

    /**
     * 求1个byte数组的16位MD指纹，不接受null值
     *
     * @param bytes 目标数组：不能为null，否则抛出空指针异常
     * @return 该文件的MD5指纹【小写形式】
     */
    public static String md5Short(byte[] bytes) {
        return md5(bytes).substring(8, 24);
    }

    /**
     * 求1个字符串的32位MD指纹，不接受null值
     *
     * @param str 目标字符串：不能为null，否则抛出空指针异常
     * @return 该文件的MD5指纹【小写形式】
     */
    public static String md5(String str) {
        Preconditions.checkNotNull(str);
        return Hashing.md5().newHasher().putString(str, Charsets.UTF_8).hash().toString();
    }

    /**
     * 求1个字符串的16位MD指纹，不接受null值
     *
     * @param str 目标字符串：不能为null，否则抛出空指针异常
     * @return 该文件的MD5指纹【小写形式】
     */
    public static String md5Short(String str) {
        return md5(str).substring(8, 24);
    }

    /**
     * 求1个字符串的160位sha指纹，不接受null值（不推荐使用，除非因为历史遗留问题而别无选择；否则建议使用sha256）
     *
     * @param str 目标字符串：不能为null，否则抛出空指针异常
     * @return 该文件的sha指纹【小写形式】
     * @deprecated
     */
    public static String sha1(String str) {
        Preconditions.checkNotNull(str);
        return Hashing.sha1().newHasher().putString(str, Charsets.UTF_8).hash().toString();
    }

    /**
     * 求1个字符串的256位sha指纹，不接受null值
     *
     * @param str 目标字符串：不能为null，否则抛出空指针异常
     * @return 该文件的sha指纹【小写形式】
     */
    public static String sha256(String str) {
        Preconditions.checkNotNull(str);
        return Hashing.sha256().newHasher().putString(str, Charsets.UTF_8).hash().toString();
    }

    /**
     * 转义字符串中的html敏感字符
     *
     * @param str 目标字符串：不能为null，否则抛出空指针异常
     * @return 转义完毕的字符串
     */
    public static String escapeHtml(String str) {
        Preconditions.checkNotNull(str);
        return HtmlEscapers.htmlEscaper().escape(str);
    }

    /**
     * base64编码
     *
     * @param bytes 目标数组（明文）：不能为null，否则抛出空指针异常
     * @return 编码结果
     */
    public static String encodeBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * base64解码
     *
     * @param str 目标字符串（密文）：不能为null，否则抛出空指针异常
     * @return 解码结果
     */
    public static byte[] decodeBase64(String str) {
        return Base64.getDecoder().decode(str);
    }

    /**
     * Base64编码, URL安全.(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548)。
     *
     * @param input 待编码的byte数组
     * @return 编码结果
     */
    public static String encodeBase64UrlSafe(byte[] input) {
        return BaseEncoding.base64Url().encode(input);
    }

    /**
     * Base64解码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
     *
     * @param input 待解码的字符串
     * @return 解码结果
     * @throws IllegalArgumentException 如果字符不合法，抛出IllegalArgumentException
     */
    public static byte[] decodeBase64UrlSafe(CharSequence input) {
        return BaseEncoding.base64Url().decode(input);
    }
}
