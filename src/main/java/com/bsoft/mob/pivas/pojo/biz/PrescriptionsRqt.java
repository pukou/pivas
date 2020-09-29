package com.bsoft.mob.pivas.pojo.biz;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 请求获取医嘱列表
 * Created by huangy on 2015-04-02.
 */
public class PrescriptionsRqt {

    /**
     * 开始时间
     */
    private String begin;

    /**
     * 结束时间
     */
    private String end;

    /**
     * 1 按签摆药归类；2 摆药核对归类；3 摆药计费归类；4 成品核对归类；
     */
    @NotNull
    private byte type;

    /**
     * 病区ID
     */
    @NotEmpty
    private String bqId;


    public String getBqId() {
        return bqId;
    }

    public void setBqId(String bqId) {
        this.bqId = bqId;
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

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
