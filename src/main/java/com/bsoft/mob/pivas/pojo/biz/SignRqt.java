package com.bsoft.mob.pivas.pojo.biz;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * 请求执行精准签收
 * Created by huangy on 2015-04-02.
 */
public class SignRqt {

    /**
     * 医嘱标签
     */
    @NotEmpty
    private String code;

    /**
     * 医嘱标签列表
     */
    @NotEmpty
    private List<String> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
