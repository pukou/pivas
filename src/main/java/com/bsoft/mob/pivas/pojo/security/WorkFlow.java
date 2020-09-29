package com.bsoft.mob.pivas.pojo.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * PDA启用的工作流程
 * Created by huangy on 2015-04-21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkFlow {

    /**
     * 静配药房ID
     */
    @JsonProperty("JPBH")
    public String JPBH;

    /**
     * JGID
     */
    @JsonProperty("JGID")
    public String JGID;

    /**
     * 当前配制药房启用的工作模块
     */
    @JsonProperty("mkbhs")
    public List<String> mkbhs;


    public WorkFlow() {

    }

    public WorkFlow( List<String> mkbhs) {
        this.mkbhs = mkbhs;
    }
}
