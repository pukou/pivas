package com.bsoft.mob.pivas.persistence.mob;

import com.bsoft.mob.pivas.domain.mob.DBJL;
import com.bsoft.mob.pivas.domain.mob.JPLC;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 打包装箱记录Mapper,对应：JM_DBJL
 * Created by huangy on 2015-04-16.
 */
public interface DBJLMapper {


    DBJL getDBJL(@Param("dbtm") String dbtm);
}
