package com.bsoft.mob.pivas.pojo.biz;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 标签查询请求
 * Created by huangy on 2015-04-02.
 */
public class GetSF01sRqt {


    /**
     * 病区ID
     */
    private String bq;

    /**
     * 批次
     */
    private String pc;

    /**
     * 配制方式
     */
    private String pzfs;

    /**
     * 主药类型
     */
    private String zylx;

    /**
     * 时间, 1 表示今天，2表示明天; 默认今天
     */
    private int sj = 1;

    /**
     * 分组查询
     */
    private String fzcx;

    /**
     * 静配编号
     */
    @NotEmpty
    private String jpbh;

    /**
     * true为查所有，false 为查询待执行项
     */
    private boolean all;

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }


    public String getJpbh() {
        return jpbh;
    }

    public void setJpbh(String jpbh) {
        this.jpbh = jpbh;
    }

    public String getBq() {
        return bq;
    }

    public void setBq(String bq) {
        this.bq = bq;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getPzfs() {
        return pzfs;
    }

    public void setPzfs(String pzfs) {
        this.pzfs = pzfs;
    }

    public String getZylx() {
        return zylx;
    }

    public void setZylx(String zylx) {
        this.zylx = zylx;
    }

    public int getSj() {
        return sj;
    }

    public void setSj(int sj) {
        this.sj = sj;
    }

    public String getFzcx() {
        return fzcx;
    }

    public void setFzcx(String fzcx) {
        this.fzcx = fzcx;
    }
}
