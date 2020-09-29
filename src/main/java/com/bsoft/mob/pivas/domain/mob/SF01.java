package com.bsoft.mob.pivas.domain.mob;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 审方01(JM_SF01)
 * Created by huangy on 2015-04-22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SF01 implements Serializable{

    private static final long serialVersionUID = -1548716840221546213L;
    @JsonProperty("JLXH")
    public Long JLXH;

    @JsonIgnore
    public String JGID;

    @JsonIgnore
    public String JPBH;

    @JsonProperty("ZYH")
    public String ZYH;

    @JsonProperty("BRBQ")
    public String BRBQ;

    @JsonProperty("BQMC")
    public String BQMC;

    @JsonProperty("BRCH")
    public String BRCH;

    @JsonProperty("BRXM")
    public String BRXM;

    @JsonProperty("ZYHM")
    public String ZYHM;

    @JsonProperty("BRXB")
    public Byte BRXB;

    @JsonProperty("BRNL")
    public String BRNL;

    @JsonProperty("LSYZ")
    public String LSYZ;

    @JsonProperty("PCID")
    public String PCID;

    @JsonIgnore
    public String PZZH;

    @JsonProperty("ZLSJ")
    public String ZLSJ;

    @JsonProperty("TXM")
    public String TXM;

    @JsonProperty("TXM_GZ")
    public String TXM_GZ;

    /**
     * 1 配制 ；0 打包
     */
    @JsonProperty("PZFS")
    public String PZFS;

    /**
     * MOB_XTPZ.DMLB=603
     1 已审方 2 已打印 3 已摆药 4 已摆药核对 5已停嘱 6已计费
     7 已打包 8 已成品核对 9 已签收 10 已拒签  11 已退药
     */
    @JsonProperty("ZTBZ")
    public int ZTBZ;

    /**
     * 1 普输 2抗生素 3TPN 4细胞毒
     */
    @JsonProperty("ZYLX")
    public Byte ZYLX;

    @JsonIgnore
    public Byte TZBZ;

    @JsonIgnore
    public Byte THBZ;

    @JsonProperty("SHHD")
    public Byte SHHD;

    @JsonProperty("SHGH")
    public String SHGH;

    @JsonProperty("SHSJ")
    public String SHSJ;

    @JsonProperty("SFWB")
    public Byte SFWB;

    @JsonProperty("DYCS")
    public Byte DYCS;

    @JsonProperty("DYGH")
    public String DYGH;

    @JsonIgnore
    public String DYSJ;

    @JsonProperty("DYDH")
    public String DYDH;

    @JsonProperty("BYBZ")
    public Byte BYBZ;

    @JsonProperty("BYGH")
    public String BYGH;

    @JsonProperty("BYSJ")
    public String BYSJ;

    @JsonProperty("BYHD")
    public Byte BYHD;

    @JsonProperty("BYHDGH")
    public String BYHDGH;

    @JsonProperty("BYHDSJ")
    public String BYHDSJ;

    @JsonProperty("FYZT")
    public Byte FYZT;

    @JsonProperty("FYRQ")
    public String FYRQ;

    @JsonProperty("FYGH")
    public String FYGH;

    @JsonProperty("CPHD")
    public Byte CPHD;

    @JsonProperty("CPHDSJ")
    public String CPHDSJ;

    @JsonProperty("CPHDGH")
    public String CPHDGH;

    @JsonProperty("DBHD")
    public Byte DBHD;

    @JsonProperty("DBHDSJ")
    public String DBHDSJ;

    @JsonProperty("DBHDGH")
    public String DBHDGH;

    @JsonProperty("FLAG")
    public Byte FLAG;

    @JsonProperty("DBPC")
    public String DBPC;

    @JsonProperty("BQQS")
    public Byte BQQS;

    @JsonProperty("QSGH")
    public String QSGH;

    @JsonProperty("QSSJ")
    public String QSSJ;

    @JsonProperty("JQYY")
    public String JQYY;

    @JsonProperty("TYZT")
    public Byte TYZT;

    @JsonProperty("TYGH")
    public String TYGH;

    @JsonProperty("TYRQ")
    public String TYRQ;


    // 关联JM_ZLSJ（治疗时间表）
    @JsonProperty("PCMC")
    public String PCMC;

}
