package com.bsoft.mob.pivas.persistence.mob;

import com.bsoft.mob.pivas.domain.mob.TZGZ;
import com.bsoft.mob.pivas.pojo.security.BarCodePrefix;
import org.apache.ibatis.annotations.Param;

/**
 * 停嘱判断规则(JM_TZGZ)
 * Created by huangy on 2015-04-22.
 */
public interface TZGZMapper {


    /**
     * 获取停嘱规则
     *
     * @param ksdm 科室代码
     * @return
     */
    TZGZ getTZGZ(@Param("KSDM") String ksdm);
}
