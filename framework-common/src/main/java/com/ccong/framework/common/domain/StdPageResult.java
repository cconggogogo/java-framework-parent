package com.ccong.framework.common.domain;

import java.io.Serializable;
import java.util.List;

public class StdPageResult<T extends Serializable> implements Serializable {

    private Integer total;

    private List<T> elements;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "StdPageResult{" +
                "total=" + total +
                ", elements=" + elements +
                '}';
    }
}
