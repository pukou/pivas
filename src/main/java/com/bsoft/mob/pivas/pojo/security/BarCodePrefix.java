package com.bsoft.mob.pivas.pojo.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 条码前缀列表
 * Created by huangy on 2015-04-21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BarCodePrefix {

    /**
     * 胸卡条码前缀
     */
    @JsonProperty("xkQz")
    public String xkQz;

    /**
     * 标签条码前缀
     */
    @JsonProperty("bqQz")
    public String bqQz;

    /**
     * 打包条码前缀
     */
    @JsonProperty("dbQz")
    public String dbQz;

    /**
     * 静配编号
     */
    @JsonProperty("JPBH")
    public String JPBH;
}
