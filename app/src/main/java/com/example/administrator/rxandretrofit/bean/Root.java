package com.example.administrator.rxandretrofit.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */

public class Root {
    private int count;

    private int start;

    private int total;

    private List<Books> books;

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStart() {
        return this.start;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return this.total;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }

    public List<Books> getBooks() {
        return this.books;
    }
}
