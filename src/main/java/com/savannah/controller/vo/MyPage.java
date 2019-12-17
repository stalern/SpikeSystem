package com.savannah.controller.vo;

/**
 * @author stalern
 * @date 2019/12/17~16:33
 */
public class MyPage {
    /**
     * 每页有多少条
     */
    private int size;
    /**
     * 第几页
     */
    private int page;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "MyPage{" +
                "size=" + size +
                ", page=" + page +
                '}';
    }
}
