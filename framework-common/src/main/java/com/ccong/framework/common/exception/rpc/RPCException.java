package com.ccong.framework.common.exception.rpc;


import com.ccong.framework.common.exception.CcongRuntimeException;

public class RPCException extends CcongRuntimeException {

    protected String originalMessage;

    public RPCException(String msg) {
        super(msg);
        this.originalMessage = msg;
    }

    public RPCException(String msg, Throwable cause) {
        super(msg, cause);
        this.originalMessage = msg;
    }

    @Override
    public String getOriginalMessage() {
        return originalMessage;
    }
}
