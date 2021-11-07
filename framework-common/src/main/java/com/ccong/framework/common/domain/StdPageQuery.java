package com.ccong.framework.common.domain;

import java.io.Serializable;

public class StdPageQuery implements Serializable {

    private Integer offset;

    private Integer limit;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "StdPageQuery{" +
                "offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
