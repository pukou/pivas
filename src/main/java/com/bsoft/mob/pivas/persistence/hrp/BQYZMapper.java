package com.bsoft.mob.pivas.persistence.hrp;

import com.bsoft.mob.pivas.domain.hrp.HSQL;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 病区医嘱 对应 视图 V_MOB_HIS_BQYZ
 * Created by huangy on 2015-04-22.
 */
public interface BQYZMapper {


    /**
     * 在V_MOB_HIS_BQYZ中获取停嘱时间（TZSJ）
     *
     * @param yzzh 医嘱组号
     * @return
     */
    String getTZSJ(@Param("YZZH") String yzzh);
}
