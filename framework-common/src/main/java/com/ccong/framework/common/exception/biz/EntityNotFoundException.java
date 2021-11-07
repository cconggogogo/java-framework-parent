package com.ccong.framework.common.exception.biz;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String msg) {
        super(msg);
    }

    private EntityNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EntityNotFoundException(String msg, String entityId, Class entityClass) {
        super("entity not found, entity id was " + entityId + ", entity class was " + entityClass.getCanonicalName() + ", details: " + msg);
        super.originalMessage = msg;
    }
}

