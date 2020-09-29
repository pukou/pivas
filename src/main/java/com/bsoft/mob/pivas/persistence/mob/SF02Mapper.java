package com.bsoft.mob.pivas.persistence.mob;

import com.bsoft.mob.pivas.domain.mob.SF02;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审方02 对应（JM_SF02）
 * Created by huangy on 2015-04-23.
 */
public interface SF02Mapper {


    public List<SF02> getSF02s(@Param("JLXH") Long jlxh);
}
