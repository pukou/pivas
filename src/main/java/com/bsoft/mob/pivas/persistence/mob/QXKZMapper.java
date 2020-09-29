package com.bsoft.mob.pivas.persistence.mob;

import com.bsoft.mob.cache.LoginUser;
import com.bsoft.mob.pivas.domain.mob.QXKZ;
import com.bsoft.mob.pivas.domain.portal.XTYH;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 静配人员权限（表：JM_QXKZ）
 * Created by huangy on 2015-04-14.
 */
public interface QXKZMapper {

    /**
     * 获取护士所持有的静配中心列表
     *
     * @param ygdm 用户ID
     * @param jgid
     * @return
     */
    List<QXKZ> getAuthories(@Param("ygdm") String ygdm, @Param("jgid") String jgid);
}
