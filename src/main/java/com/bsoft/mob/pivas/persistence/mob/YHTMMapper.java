package com.bsoft.mob.pivas.persistence.mob;

import com.bsoft.mob.pivas.domain.mob.YHTM;
import org.apache.ibatis.annotations.Param;

/**
 * 用户条码（MOB_YHTM）
 * Created by huangy on 2015-04-16.
 */
public interface YHTMMapper {


    public YHTM getYHTM(@Param("barcode") String barcode);
}
