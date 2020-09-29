package com.bsoft.mob.pivas.domain.mob;

import java.io.Serializable;

/**
 * 停嘱判断规则(JM_TZGZ)
 * Created by huangy on 2015-04-22.
 */
public class TZGZ implements Serializable{

    private static final long serialVersionUID = 6687627462521257439L;
    /**
     * id
     */
    public String GZXH;
    /**
     * 科室代码
     */
    public String KSDM;

    /**
     * 规则类型
     */
    public byte GZLX;

    /**
     * 规则时间
     */
    public String GZSJ;
}
