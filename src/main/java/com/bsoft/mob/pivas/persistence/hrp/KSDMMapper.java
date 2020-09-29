package com.bsoft.mob.pivas.persistence.hrp;

import com.bsoft.mob.pivas.domain.hrp.KSDM;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 科室代码 对应 视图 V_MOB_HIS_KSDM
 * Created by huangy on 2015-04-22.
 */
public interface KSDMMapper {


    /**
     * bqids 不能为NULL
     *
     * @param bqids
     * @return
     */
    List<KSDM> getOffices(@Param("array") String... bqids);

    /**
     * 获取所有病区
     * @return
     */
    List<KSDM> getAllOffices();
}
