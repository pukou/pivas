package com.bsoft.mob.pivas.domain.hrp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 护士权力 对应 视图 V_MOB_HIS_HSQL
 * Created by huangy on 2015-04-15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HSQL implements Serializable{

    private static final long serialVersionUID = -6558519478929285668L;
    /**
     * 员工代码
     */
    @JsonIgnore
    public String YGDM;

    /**
     * 科室代码
     */
    @JsonProperty("KSDM")
    public String KSDM;

    /**
     * 默认标志
     */
    @JsonProperty("MRBZ")
    public String MRBZ;

    /**
     * 机构ID
     */
    @JsonIgnore
    public String JGID;

    //附加 科室代码 V_MOB_HIS_KSDM
    /**
     * 科室名称
     */
    @JsonProperty("KSMC")
    public String KSMC;


    //附加 静配启用病区 JM_QYBQ
    /**
     * 静配编号
     */
    @JsonProperty("JPBH")
    public String JPBH;
}
