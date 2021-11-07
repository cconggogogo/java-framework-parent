package com.ccong.framework.common.domain;


import com.ccong.framework.common.lang.IdGenerator;

import java.io.Serializable;

public class StdRequest<T extends Serializable> implements Serializable {

    /**
     * 调用方信息，全部使用【小写字母】组成，类似HTTP里面的user-agent
     * 第1位代表调用方是谁，包括：go、php、c++、h5、www、android、ios
     * 第2位代表业务码，例如check_upgrade
     * 不同的位之间用“;”分隔开，注意是英文分号
     * go;业务名
     * php;业务名
     * c++;业务名
     * h5;业务名
     * www;业务名
     * android;业务名
     * ios;业务名
     */
    private String ua;

    /**
     * traceId，用于调用链路追踪和实现幂等操作
     */
    private String tid;

    /**
     * 请求时间
     */
    private Long ts;

    /**
     * 请求体
     */
    private T data;

    public static <T extends Serializable> StdRequest<T> create(T data) {
        StdRequest<T> rpcRequest = new StdRequest<>();
        rpcRequest.setTs(System.currentTimeMillis());
        rpcRequest.setTid(IdGenerator.uuid());
        rpcRequest.setData(data);
        return rpcRequest;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StdRequest{" +
                "tid='" + tid + '\'' +
                ", ts=" + ts +
                ", data=" + data +
                '}';
    }
}
