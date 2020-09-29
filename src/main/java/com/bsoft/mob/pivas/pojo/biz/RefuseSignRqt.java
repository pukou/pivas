package com.bsoft.mob.pivas.pojo.biz;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 拒绝签收请求
 * Created by huangy on 2015-04-02.
 */
public class RefuseSignRqt {

    /**
     * 医嘱标签
     */
    @NotEmpty
    private String code;


    /**
     * 拒绝原因类型
     */
    @NotEmpty
    private String yyxh;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getYyxh() {
        return yyxh;
    }

    public void setYyxh(String yyxh) {
        this.yyxh = yyxh;
    }


}
