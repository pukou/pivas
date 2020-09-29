package com.bsoft.mob.pivas.domain.hrp;

import java.io.Serializable;

/**
 * 病区医嘱（V_MOB_HIS_BQYZ）
 * Created by huangy on 2015-04-22.
 */
public class BQYZ implements Serializable {

    private static final long serialVersionUID = 5823257695375790059L;
    /**
     * ID
     */
    public String JLXH;

    /**
     * 医嘱组号
     */
    public String YZZH;

    /**
     * 停嘱时间
     */
    public String TZSJ;
}
