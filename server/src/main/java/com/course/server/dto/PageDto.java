package com.course.server.dto;

import java.util.List;

public class PageDto<T> {
    /**
     * 前端传入
     */
    protected int pageNum;//当前页
    protected int PageSize;//页码
    /**
     * 后端查询出的结果
     */
    protected long totalNum;//总记录数
    protected List<T> list;//数据集合

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageDto{" +
                "pageNum=" + pageNum +
                ", PageSize=" + PageSize +
                ", totalNum=" + totalNum +
                ", list=" + list +
                '}';
    }
}
