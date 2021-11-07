package com.ccong.framework.common.domain;

import com.ccong.framework.common.lang.IdGenerator;
import com.ccong.framework.common.net.HttpStatus;

import java.io.Serializable;

public class StdResponse<T extends Serializable> implements Serializable {

    public static final int OK = HttpStatus.OK.value();
    public static final int CREATED = HttpStatus.CREATED.value();
    public static final int MOVED_PERMANENTLY = HttpStatus.MOVED_PERMANENTLY.value();
    public static final int FOUND = HttpStatus.FOUND.value();
    public static final int NOT_MODIFIED = HttpStatus.NOT_MODIFIED.value();
    public static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
    public static final int UNAUTHORIZED = HttpStatus.UNAUTHORIZED.value();
    public static final int FORBIDDEN = HttpStatus.FORBIDDEN.value();
    public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
    public static final int INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

    /**
     * traceId，用于调用链路追踪和实现幂等操作
     */
    private String tid;

    /**
     * 响应时间
     */
    private Long ts;

    /**
     * 响应代码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String msg;

    /**
     * 数据体
     */
    private T data;

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private static <T extends Serializable> StdResponse<T> newFailure(int code, String msg) {
        StdResponse<T> res = new StdResponse<>();
        res.setTid(IdGenerator.uuid());
        res.setTs(System.currentTimeMillis());
        res.setCode(BAD_REQUEST);
        res.setMsg(msg);
        return res;
    }

    private static <T extends Serializable> StdResponse<T> newSuccess(String msg, T data) {
        StdResponse<T> res = new StdResponse<>();
        res.setTid(IdGenerator.uuid());
        res.setTs(System.currentTimeMillis());
        res.setCode(OK);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }

    public boolean isSuccess() {
        return this.getCode() != null && this.getCode() == OK;
    }

    public boolean isSuccessWithData() {
        return this.getCode() != null && this.getCode() == OK && data != null;
    }

    public static <T extends Serializable> StdResponse<T> newOk() {
        return newSuccess("", null);
    }

    public static <T extends Serializable> StdResponse<T> newOk(T data) {
        return newSuccess("", data);
    }

    public static <T extends Serializable> StdResponse<T> newOk(String msg, T data) {
        return newSuccess(msg, data);
    }

    public static <T extends Serializable> StdResponse<T> newBadRequest(String msg) {
        return newFailure(BAD_REQUEST, msg);
    }

    public static <T extends Serializable> StdResponse<T> newInternalServerError(String msg) {
        return newFailure(INTERNAL_SERVER_ERROR, msg);
    }

    @Override
    public String toString() {
        return "StdRequest{" +
                "tid='" + tid + '\'' +
                ", ts=" + ts +
                ", code=" + code +
                ", msg=" + msg +
                ", data=" + data +
                '}';
    }
}
