package com.ccong.framework.common.exception.security;

public class CredentialsNotFoundException extends AuthenticationException {

    public CredentialsNotFoundException(String msg) {
        super(msg);
    }

    private CredentialsNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CredentialsNotFoundException(String msg, String key) {
        super("credentials not found by " + key + ", details: " + msg);
        super.originalMessage = msg;
    }
}
