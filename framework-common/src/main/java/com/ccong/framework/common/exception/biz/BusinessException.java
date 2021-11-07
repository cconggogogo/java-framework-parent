package com.ccong.framework.common.exception.biz;

import com.ccong.framework.common.exception.CcongRuntimeException;

// 通用业务错误
public class BusinessException extends CcongRuntimeException {

    protected String originalMessage;


    public BusinessException(String msg) {
        super(msg);
        this.originalMessage = msg;
    }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
        this.originalMessage = msg;
    }

    @Override
    public String getOriginalMessage() {
        return originalMessage;
    }
}
