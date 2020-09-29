package com.bsoft.mob.pivas.pojo.biz;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 标签汇总查询请求
 * Created by huangy on 2015-04-02.
 */
public class StatisAllRqt {

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
     * 病区维度
     */
    @NotEmpty
    private String bqValue;

    /**
     * 药品类别
     */
    @NotEmpty
    private String ypValue;

    /**
     * 配制批次
     */
    @NotEmpty
    private String pcValue;


    /**
     * 配制方式
     */
    @NotEmpty
    private String fsValue;

    /**
     * 静配编号
     */
    @NotEmpty
    private String jpbh;


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

    public String getBqValue() {
        return bqValue;
    }

    public void setBqValue(String bqValue) {
        this.bqValue = bqValue;
    }

    public String getYpValue() {
        return ypValue;
    }

    public void setYpValue(String ypValue) {
        this.ypValue = ypValue;
    }

    public String getPcValue() {
        return pcValue;
    }

    public void setPcValue(String pcValue) {
        this.pcValue = pcValue;
    }

    public String getFsValue() {
        return fsValue;
    }

    public void setFsValue(String fsValue) {
        this.fsValue = fsValue;
    }

    public String getJpbh() {
        return jpbh;
    }

    public void setJpbh(String jpbh) {
        this.jpbh = jpbh;
    }
}
