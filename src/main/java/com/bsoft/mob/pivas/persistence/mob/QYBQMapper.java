package com.bsoft.mob.pivas.persistence.mob;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 静配流程控制Mapper,对应：JM_JPLC
 * Created by huangy on 2015-04-16.
 */
public interface QYBQMapper {

    String getJPBH(@Param("ksdm") String ksdm);

    /**
     * 根据静配编号获取病区IDS
     *
     * @param jpbh
     * @return 病区ID列表
     */
    String[] getOffices(@Param("jpbh") String jpbh);
}
