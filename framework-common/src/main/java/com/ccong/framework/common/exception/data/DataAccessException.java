package com.ccong.framework.common.exception.data;

import com.ccong.framework.common.exception.CcongRuntimeException;

public class DataAccessException extends CcongRuntimeException {

    protected String originalMessage;

    public DataAccessException(String msg) {
        super(msg);
        this.originalMessage = msg;
    }

    public DataAccessException(String msg, Throwable cause) {
        super(msg, cause);
        this.originalMessage = msg;
    }

    @Override
    public String getOriginalMessage() {
        return originalMessage;
    }
}
