package com.bsoft.mob.pivas.persistence.hrp;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 病区医嘱 对应 视图 V_MOB_HIS_BQYZ
 * Created by huangy on 2015-04-22.
 */
public interface MKIDMapper {


    /**
     * 获取停嘱MKID（MKID）
     *
     * @param YGDM 员工代码
     * @return
     */
    List<String> getMKIDByYGDM(@Param("JGID") String jgid, @Param("YGDM") String YGDM);
}
