package com.bsoft.mob.pivas.domain.hrp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 科室代码 对应 V_MOB_HIS_KSDM
 * Created by huangy on 2015-05-04.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class KSDM implements Serializable{

    private static final long serialVersionUID = -3104757805982633671L;

    public String KSDM;

    public String KSMC;

    public String JGID;
}
