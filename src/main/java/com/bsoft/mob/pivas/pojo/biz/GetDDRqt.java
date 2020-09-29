package com.bsoft.mob.pivas.pojo.biz;

/**
 * 获取医嘱信息请求
 * Created by huangy on 2015-04-28.
 */
public class GetDDRqt {

    /**
     * 医嘱条码
     */
    private String code;

    /**
     * true表示要检测是否停嘱，false 表示不检测
     */
    private boolean checkStop;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isCheckStop() {
        return checkStop;
    }

    public void setCheckStop(boolean checkStop) {
        this.checkStop = checkStop;
    }
}
