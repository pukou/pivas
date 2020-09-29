package com.bsoft.mob.pivas.domain.portal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 系统用户，对应： V_MOB_PORTAL_XTYH 视图
 * Created by huangy on 2015-04-14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class XTYH implements Serializable {

    private static final long serialVersionUID = 1015241333153665604L;

    @JsonProperty("YHID")
    public String YHID;

    @JsonProperty("YHDM")
    public String YHDM;

    @JsonProperty("YHXM")
    public String YHXM;

    @JsonProperty("YHMM")
    public String YHMM;

    @JsonIgnore
    public String JGID;
}
