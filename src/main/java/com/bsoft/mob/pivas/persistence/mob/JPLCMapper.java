package com.bsoft.mob.pivas.persistence.mob;

import com.bsoft.mob.pivas.domain.mob.JPLC;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 静配流程控制Mapper,对应：JM_JPLC
 * Created by huangy on 2015-04-16.
 */
public interface JPLCMapper {


    /**
     * 获取当前静配中心所启动有工作模块
     *
     * @param jpid
     * @return
     */
    List<String> getPDAWorkFlow(@Param("JGID") String jgid, @Param("JPBH") String jpid, @Param("YGDM") String ygdm);

    JPLC getWorkFlow(@Param("jpbh") String jpbh, @Param("mkbh") int mkbh);

    /**
     * 返回要统计的模块编号，模块名称 列表
     *
     * @param jpbh
     * @return MAP 包含 MKBH,和MKMC 两个key
     */
    List<Map<String, Object>> getTJWorkFlow(@Param("jpbh") String jpbh);
}
