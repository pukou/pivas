package com.bsoft.mob.pivas.pojo.biz;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 标签汇总查询请求
 * Created by huangy on 2015-04-02.
 */
public class StatisMyRqt {

    /**
     * 开始时间
     */
    @NotEmpty
    private String begin;

    /**
     * 结束时间
     */
    @NotEmpty
    private String end;


    /**
     * 静配编号
     */
    @NotEmpty
    private String jpbh;

    public String getJpbh() {
        return jpbh;
    }

    public void setJpbh(String jpbh) {
        this.jpbh = jpbh;
    }


    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
