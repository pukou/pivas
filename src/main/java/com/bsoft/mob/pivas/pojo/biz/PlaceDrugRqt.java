package com.bsoft.mob.pivas.pojo.biz;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 请求执行精准摆药
 * Created by huangy on 2015-04-02.
 */
public class PlaceDrugRqt {

    /**
     * 医嘱标签
     */
    @NotEmpty
    private String code;

    /**
     * 药品列表总数
     */
    @NotNull
    private int count;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
