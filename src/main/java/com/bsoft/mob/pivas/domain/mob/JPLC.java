package com.bsoft.mob.pivas.domain.mob;

import java.io.Serializable;

/**
 * 静配流程控制 (JM_JPLC)
 * Created by huangy on 2015-05-05.
 */
public class JPLC implements Serializable{

    private static final long serialVersionUID = 3589813045062858478L;
    public String JLXH;

    public String JGID;

    public String JPBH;

    public String MKBH;

    public Boolean SYBZ;

    public Byte QZMK;

    /**
     * 用于标识PDA使用
     */
    public Byte PDA;

    /**
     * 用于标识工作量统计
     */
    public Byte GZTJ;

}
