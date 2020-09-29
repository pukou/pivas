package com.bsoft.mob.pivas.persistence.mob;

import org.apache.ibatis.annotations.Param;

/**
 * Created by fengxici on 2015/10/15.
 */
public interface WorkFlowMapper {

    int check(@Param("ztzd") String ztzd,@Param("jlxh") long jlxh);
}
