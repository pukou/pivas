package com.bsoft.mob.pivas.domain.mob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 打包装箱记录 JM_DBJL
 * Created by huangy on 2015/6/10.
 */
public class DBJL implements Serializable{

    private static final long serialVersionUID = 1658573744659457067L;
    /**
     * 打包序号
     */
    @JsonIgnore
    public String DBXH;


    @JsonIgnore
    public String JGID;
    @JsonIgnore
    public String JPBH;

    /**
     * 打包病区
     */
    @JsonProperty("BRBQ")
    public String BRBQ;

    /**
     * 打包批次
     */
    @JsonProperty("DBPC")
    public String DBPC;
    /**
     * 总袋数
     */
    @JsonProperty("ZDS")
    public String ZDS;

    /**
     * 打包条码
     */
    @JsonProperty("DBTM")
    public String DBTM;
    /**
     * 打包人
     */
    @JsonProperty("DBGH")
    public String DBGH;


    //额外关连属性
    //病区名称
    @JsonProperty("BQMC")
    public String BQMC;

    //批次名称
    @JsonProperty("PCMC")
    public String PCMC;

    //打包人姓名
    @JsonProperty("DBRY")
    public String DBRY;
}
