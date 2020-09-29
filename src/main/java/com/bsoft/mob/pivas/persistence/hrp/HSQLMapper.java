package com.bsoft.mob.pivas.persistence.hrp;

import com.bsoft.mob.pivas.domain.hrp.HSQL;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 护士权力 对应 视图 V_MOB_HIS_HSQL
 * Created by huangy on 2015-04-15.
 */
public interface HSQLMapper {


    /**
     * 获取护士所持有的病区列表
     *
     * @param ygdm
     * @param jgid
     * @return
     */
    List<HSQL> getAuthories(@Param("ygdm") String ygdm, @Param("jgid") String jgid);

}
