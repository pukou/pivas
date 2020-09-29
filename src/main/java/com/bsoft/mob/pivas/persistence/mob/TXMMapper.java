package com.bsoft.mob.pivas.persistence.mob;

import com.bsoft.mob.pivas.pojo.security.BarCodePrefix;
import org.apache.ibatis.annotations.Param;

/**
 * 条形码维护（JM_TXM）
 * Created by huangy on 2015-04-21.
 */
public interface TXMMapper {


    BarCodePrefix getBarCodePrefixs(@Param("JGID")String jgid,@Param("JPBH") String jpid);
}
