package com.ccong.framework.common.domain;

import java.beans.ConstructorProperties;
import java.io.Serializable;

/**
 * 给前端输出数据用的Vo包裹类
 * @param <T>
 */
public class ResultVo<T> implements Serializable {

    public static final int SUCCESS_CODE = 0;
    public static final int ERROR_CODE = -1;
    public static final int BAD_REQUEST_CODE = 400;
    public static final int SERVER_ERROR_CODE = 500;
    public static final int USER_FORBIDDEN_CODE = 403;
    public static final int NOT_DSP_USER_CODE = 417;
    public static final int REDIRECT_CODE = 302;
    private int error;
    private String msg;
    private T data;
    private String redirectUrl;

    public boolean success() {
        return this.getError() == 0;
    }

    public static <T> ResultVo<T> asSuccess(T result) {
        return new ResultVo<>(0, "操作成功", result, (String) null);
    }

    public static ResultVo asSuccess() {
        return asSuccess((Object) null);
    }

    public static <T> ResultVo<T> asError(String msg) {
        return new ResultVo<>(-1, msg, null, (String) null);
    }

    public static <T> ResultVo<T> asError(String msg, T data) {
        return new ResultVo<>(-1, msg, data, (String) null);
    }

    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public static ResultVo asErrorAndRedirect(String redirectUrl) {
        return new ResultVo<>(900, "", (Object) null, redirectUrl);
    }

    public static ResultVo asErrorWithCode(int code, String msg) {
        return new ResultVo<>(code, msg, (Object) null, null);
    }

    public static ResultVo asDeniedAccess(String redirectUrl) {
        return new ResultVo<>(USER_FORBIDDEN_CODE, "user has no right to access", null, redirectUrl);
    }

    public static ResultVo asRedirect(String redirectUrl) {
        return new ResultVo<>(REDIRECT_CODE, "redirect", null, redirectUrl);
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
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

    @ConstructorProperties({"error", "msg", "data", "redirectUrl"})
    public ResultVo(int error, String msg, T data, String redirectUrl) {
        this.error = error;
        this.msg = msg;
        this.data = data;
        this.redirectUrl = redirectUrl;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "error=" + error +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", redirectUrl='" + redirectUrl + '\'' +
                '}';
    }
}