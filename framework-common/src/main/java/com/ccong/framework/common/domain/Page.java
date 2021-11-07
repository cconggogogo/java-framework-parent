package com.ccong.framework.common.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 与前端交互的分页数据对象
 */
public class Page<E> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4973539948210269342L;
    /**
     * 默认页码
     */
    public static final int DEFAULT_PAGE_NO = 1;
    /**
     * 默认页面大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    /**
     * 默认的快速导航页码显示个数
     */
    public static final int DEFAULT_PAGE_NAV_SIZE = 5;

    private int pageNo = DEFAULT_PAGE_NO; // 页码
    private int pageSize = DEFAULT_PAGE_SIZE; // 页面大小
    private int pageNavSize = DEFAULT_PAGE_NAV_SIZE; // 页码快速导航显示的个数
    private long totalCount; // 总的记录数
    private List<E> content; // 返回的查询结果集

    public Page() {
        super();
    }

    public Page(int pageNo, int pageSize) {
        super();
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    public Page(int pageNo, int pageSize, int pageNavSize) {
        this(pageNo, pageSize);
        setPageNavSize(pageNavSize);
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        if (pageNo < 1) {
            pageNo = DEFAULT_PAGE_NO;
        }
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<E> getContent() {
        return content;
    }

    public void setContent(List<E> content) {
        this.content = content;
    }

    public int getPageNavSize() {
        return pageNavSize;
    }

    /**
     * 返回快速导航页码
     *
     * @return
     */
    public int[] getPageNavArray() {
        // 先运算出左，右边界
        int start = 0, end = 0;
        int a = pageNavSize / 2;
        start = pageNo - a;
        if (pageNavSize % 2 == 0) {
            end = pageNo + a - 1;
        } else {
            end = pageNo + a;
        }
        // 分三种情况处理
        int totalPages = getTotalPage();
        int[] b = new int[pageNavSize];
        // 左边界
        if (start < 1) {
            for (int i = 0, step = 1; i < pageNavSize; i++, step++) {
                if (step <= totalPages) {
                    b[i] = step;
                } else {
                    break;
                }
            }
        } else if (end > totalPages) {   // 右边界
            for (int i = pageNavSize - 1, step = totalPages; i >= 0; i--, step--) {
                if (step > 0) {
                    b[i] = step;
                } else {
                    break;
                }
            }
        } else {    // 中间
            for (int i = 0; i < pageNavSize; i++) {
                b[i] = start++;
            }
        }
        return b;
    }

    public void setPageNavSize(int pageNavSize) {
        if (pageNavSize < 1) {
            pageNavSize = DEFAULT_PAGE_NAV_SIZE;
        }
        this.pageNavSize = pageNavSize;
    }

    /**
     * 获得总的页码数量
     *
     * @return
     */
    public int getTotalPage() {
        if (totalCount % pageSize > 0) {
            return (int) (totalCount / pageSize + 1);
        } else {
            return (int) (totalCount / pageSize);
        }
    }

    /**
     * 获取从哪一条记录开始查询
     *
     * @return
     */
    public int getFirstIndex() {
        return (pageNo - 1) * pageSize;
    }

    /**
     * 获取最后一条记录的下标数（不包含）
     *
     * @return
     */
    public int getLastIndex() {
        return getFirstIndex() + pageSize;
    }

    /**
     * 判断是否还有下一页
     *
     * @return
     */
    public boolean isHasNextPage() {
        return (pageNo + 1) <= getTotalPage();
    }

    /**
     * 获取下一个页码，在调用之前先调用<code>isHasNextPage()</code>方法进行判断
     *
     * @return
     */
    public int getNextPage() {
        return pageNo + 1;
    }

    /**
     * 判断是否还有上一页
     *
     * @return
     */
    public boolean isHasPrePage() {
        return (pageNo - 1) > 0;
    }

    /**
     * 获取上一个页码，在调用之前先调用<code>isHasPrePage()</code>方法进行判断
     *
     * @return
     */
    public int getPrePage() {
        return pageNo - 1;
    }
}