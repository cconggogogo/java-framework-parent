package com.ccong.framework.common.exception.security;

import com.ccong.framework.common.exception.CcongRuntimeException;

public class AuthenticationException extends CcongRuntimeException {

    protected String originalMessage;

    public AuthenticationException(String msg) {
        super(msg);
        this.originalMessage = msg;
    }

    public AuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
        this.originalMessage = msg;
    }

    @Override
    public String getOriginalMessage() {
        return originalMessage;
    }
}

