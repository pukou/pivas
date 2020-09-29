package com.bsoft.mob.pivas.persistence.portal;

import com.bsoft.mob.cache.LoginUser;
import com.bsoft.mob.pivas.domain.mob.QXKZ;
import com.bsoft.mob.pivas.domain.portal.XTYH;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户Mapper
 * 对应 V_MOB_PORTAL_XTYH
 * Created by huangy on 2015-04-14.
 */
public interface XTYHMapper {

    XTYH login(LoginUser user);

    /**
     * 获取用户信息
     *
     * @param username
     * @param jgid
     * @return
     */
    XTYH getUser(@Param("username") String username, @Param("jgid") String jgid);

    XTYH getUserByYGDM(@Param("ygdm") String ygdm);

    XTYH getXTYH(@Param("yhid") String yhid);

}
