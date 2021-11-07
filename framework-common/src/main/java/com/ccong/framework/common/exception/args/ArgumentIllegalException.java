package com.ccong.framework.common.exception.args;


import com.ccong.framework.common.exception.CcongRuntimeException;

public class ArgumentIllegalException extends CcongRuntimeException {

    protected final String originalMessage;

    /**
     * Create a new ArgumentIllegalException.
     *
     * @param msg the detail message
     */
    public ArgumentIllegalException(String msg) {
        super(msg);
        this.originalMessage = msg;
    }

    /**
     * Create a new ArgumentIllegalException.
     *
     * @param msg   the detail message
     * @param cause the root cause
     */
    public ArgumentIllegalException(String msg, Throwable cause) {
        super(msg, cause);
        this.originalMessage = msg;
    }


    /**
     * Create a new ArgumentIllegalException.
     *
     * @param value the value of argument
     * @param msg   the detail message
     */
    public ArgumentIllegalException(Object value, String msg) {
        super("argument " + (value != null ? value.toString() : "null") + " illegal, because of : " + msg);
        this.originalMessage = msg;
    }


    /**
     * Create a new ArgumentIllegalException.
     *
     * @param value        the value of argument
     * @param anotherValue the value of another argument
     * @param msg          the detail message
     */
    public ArgumentIllegalException(Object value, Object anotherValue, String msg) {
        super("argument " + (value != null ? value.toString() : "null") + " illegal, another argument " + anotherValue
                + ", because of : " + msg);
        this.originalMessage = msg;
    }

    @Override
    public String getOriginalMessage() {
        return originalMessage;
    }
}
