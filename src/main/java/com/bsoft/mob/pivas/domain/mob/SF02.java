package com.bsoft.mob.pivas.domain.mob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 审方02 对应（JM_SF02）
 * Created by huangy on 2015-04-23.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SF02 implements Serializable{


    private static final long serialVersionUID = -407957947593494706L;
    @JsonProperty("JLID")
    public Long JLID;

    @JsonProperty("JLXH")
    public Long JLXH;

    /**
     * 药品名称
     */
    @JsonProperty("YPMC")
    public String YPMC;

    /**
     * 药品规格  （左 ）
     */
    @JsonProperty("YPGG")
    public String YPGG;

    /**
     * 药品数量（右）
     */
    @JsonProperty("YPSL")
    public String YPSL;

    /**
     * 一次剂量 （中）
     */
    @JsonProperty("YCJL")
    public String YCJL;

    /**
     * 剂量单位 （中）
     */
    @JsonProperty("JLDW")
    public String JLDW;

    /**
     * 条行码
     */
    @JsonProperty("YPTXM")
    public String YPTXM;

    /**
     * 数量单位（右）
     */
    @JsonProperty("SLDW")
    public String SLDW;

}
