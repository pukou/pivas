package com.bsoft.mob.pivas.domain.mob;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 用户条码（MOB_YHTM）
 * Created by huangy on 2015-04-16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YHTM implements Serializable{

    private static final long serialVersionUID = -26815386231226475L;
    /**
     * 条码编号
     */
    @JsonProperty("TMBH")
    public String TMBH;

    /**
     * 条码内容
     */
    @JsonProperty("TMNR")
    public String TMNR;

    /**
     * 员工代码
     */
    @JsonProperty("YGDM")
    public String YGDM;

    /**
     * 条码状态
     */
    @JsonProperty("TMZT")
    public String TMZT;

    /**
     * 识别系统
     */
    @JsonProperty("SBXT")
    public String SBXT;

    /**
     * 机构ID
     */
    @JsonProperty("JGID")
    public String JGID;
}
