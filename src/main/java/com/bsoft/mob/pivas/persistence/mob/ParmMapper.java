package com.bsoft.mob.pivas.persistence.mob;

import com.bsoft.mob.domain.UserParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 工作 on 2018/12/19.
 */
public interface ParmMapper {
    List<UserParams> getJpzxParm(@Param("GSJB") String var1, @Param("GSDX") String var2, @Param("array") String[] var3, @Param("JGID") String var4);
}
