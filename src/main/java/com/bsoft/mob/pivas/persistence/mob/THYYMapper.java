package com.bsoft.mob.pivas.persistence.mob;

import com.bsoft.mob.pivas.domain.mob.DBJL;
import com.bsoft.mob.pivas.domain.mob.THYY;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 退回原因 Mapper,对应：JM_THYY
 * Created by huangy on 2015-04-16.
 */
public interface THYYMapper {


    List<THYY> getTHYYs(@Param("thlx") String thlx, @Param("jgid") String jgid);


    List<String> getYYNRs(@Param("thlx") String thlx, @Param("jgid") String jgid);

}
