package com.bsoft.mob.pivas.domain.mob;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * 退回原因 JM_THYY
 * Created by huangy on 2015/6/12.
 */
public class THYY implements Serializable{

    private static final long serialVersionUID = -2414056163086646900L;
    public String YYXH;

    @JsonIgnore
    public Byte YYLX;

    public String YYNR;

    @JsonIgnore
    public String ZFBZ;

    @JsonIgnore
    public String JGID;
}
