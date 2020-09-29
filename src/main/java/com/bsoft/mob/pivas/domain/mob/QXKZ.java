package com.bsoft.mob.pivas.domain.mob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 静配人员权限（表：JM_QXKZ）
 * Created by huangy on 2015-04-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QXKZ implements Serializable{

    private static final long serialVersionUID = -2769260548400300815L;
    /**
     * 权限识别
     */
    @JsonIgnore
    public Long QXSB;

    /**
     * 机构ID
     */
    @JsonIgnore
    public String JGID;

    /**
     * 静配编号
     */
    @JsonProperty("JPBH")
    public String JPBH;

    /**
     * 员工代码
     */
    @JsonIgnore
    public String YGDM;


    /**
     * 默认标志
     */
    @JsonProperty("MRBZ")
    public Boolean MRBZ;


    //级联 JM_JPZX表（静配中心维护）
    @JsonProperty("JPMC")
    public String JPMC;

}
