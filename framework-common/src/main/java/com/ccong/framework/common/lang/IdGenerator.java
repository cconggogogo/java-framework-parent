package com.ccong.framework.common.lang;

import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 */
public class IdGenerator {

    private IdGenerator() {

    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
     */
    public static String uuid2() {
        return UUID.randomUUID().toString();
    }

}