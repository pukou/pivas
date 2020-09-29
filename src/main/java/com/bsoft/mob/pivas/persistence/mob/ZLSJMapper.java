package com.bsoft.mob.pivas.persistence.mob;

import com.bsoft.mob.pivas.domain.mob.ZLSJ;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 治疗时间维护 (JM_ZLSJ)
 * Created by huangy on 2015-05-04.
 */
public interface ZLSJMapper {

    /**
     * 返回配制批次<id,mkmc>列表
     * @param jpbh
     * @return
     */
    List<ZLSJ> getPCs(@Param("jpbh") String jpbh);
}
