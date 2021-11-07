package com.ccong.framework.common.exception.security;

public class CredentialsBadException extends AuthenticationException {

    public CredentialsBadException(String msg) {
        super(msg);
    }

    private CredentialsBadException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CredentialsBadException(String msg, String credentials) {
        super("bad credentials " + credentials + ", details: " + msg);
        super.originalMessage = msg;
    }
}