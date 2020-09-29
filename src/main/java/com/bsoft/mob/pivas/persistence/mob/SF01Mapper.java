package com.bsoft.mob.pivas.persistence.mob;

import com.bsoft.mob.pivas.domain.mob.SF01;
import com.bsoft.mob.pivas.pojo.biz.TrankMsg;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 审方01(JM_SF01)
 * Created by huangy on 2015-04-23.
 */
public interface SF01Mapper {


    /**
     * 根据医嘱条码获取审方数据
     *
     * @param barcode
     * @return 返回审方记录
     */
    SF01 getSF01(@Param("TXM") String barcode, @Param("JGID") String jgid);

    /**
     * 修改SF01中的停嘱标志字段
     *
     * @param jlxh
     * @param tzbz
     * @return 返回受影响的行数
     */
    int setTZBZ(@Param("JLXH") long jlxh, @Param("TZBZ") int tzbz);


    /**
     * 执行摆药
     *
     * @param jlxh SF01 记录序号
     * @param time 执行时间
     * @param ygdm 执行工号
     * @param ztbz
     * @return
     */
    int actionPlace(@Param("jlxh") Long jlxh, @Param("time") String time, @Param("ygdm") String ygdm, @Param("ztbz") int ztbz);

    List<SF01> getSF01s(@Param("brbq") String brbq, @Param("pcid") String pcid, @Param("pzfs") String pzfs, @Param("zylx") String zylx, @Param("fzcx") String fzcx, @Param("begin") String begin, @Param("end") String end, @Param("jgid") String jgid, @Param("jpbh") String jpbh);

    List<SF01> getTodos(@Param("brbq") String brbq, @Param("pcid") String pcid, @Param("pzfs") String pzfs, @Param("zylx") String zylx, @Param("fzcx") String fzcx, @Param("begin") String begin, @Param("end") String end, @Param("jgid") String jgid, @Param("jpbh") String jpbh);

    /**
     * 执行摆药核对
     *
     * @param jlxh SF01 记录序号
     * @param time 执行时间
     * @param ygdm 执行工号
     * @param ztbz
     * @return 返回受影响的行数
     */
    int actionPlaceCheck(@Param("jlxh") Long jlxh, @Param("time") String time, @Param("ygdm") String ygdm, @Param("ztbz") int ztbz);

    /**
     * 摆药计费
     *
     * @param jlxh
     * @param time
     * @param ygdm
     * @return 返回受影响的行数
     */
    int actionBilling(@Param("jlxh") Long jlxh, @Param("time") String time, @Param("ygdm") String ygdm);

    /**
     * 成品核对
     *
     * @param jlxh
     * @param time
     * @param ygdm
     * @param ztbz
     * @return 返回受影响的行数
     */
    int actionProductCheck(@Param("jlxh") Long jlxh, @Param("time") String time, @Param("ygdm") String ygdm, @Param("ztbz") int ztbz);

    /**
     * 根据打包条码获取未签收SF01数据列表
     *
     * @param dbtm 打包条码
     * @return
     */
    List<SF01> getSF01sByDBTM(@Param("dbtm") String dbtm);

    /**
     * 签收
     *
     * @param jlxhs
     * @param time
     * @param ygdm
     * @return 返回受影响的行数
     */
    int actionSign(@Param("array") Long[] jlxhs, @Param("time") String time, @Param("ygdm") String ygdm);

    /**
     * 拒绝签收
     *
     * @param txm  条形码
     * @param yyxh 原因序号
     * @param time
     * @param ygdm
     * @return 返回受影响的行数
     */
    int refuseSign(@Param("txm") String txm, @Param("yyxh") String yyxh, @Param("time") String time, @Param("ygdm") String ygdm);

    /**
     * 统计个人工作量
     *
     * @param mkbh
     * @param ygdm
     * @param jpbh
     * @param begin
     * @param end
     * @return
     */
    int getCount(@Param("mkbh") String mkbh, @Param("ygdm") String ygdm, @Param("jpbh") String jpbh, @Param("begin") String begin, @Param("end") String end);

    /**
     * 汇总查询-获取已操作的数目
     *
     * @param mkbh
     * @param bqValue
     * @param fsValue
     * @param pcValue
     * @param ypValue
     * @param jpbh
     * @param begin
     * @param end
     * @return
     */
    int getDoneCount(@Param("mkbh") String mkbh, @Param("bqValue") String bqValue, @Param("fsValue") String fsValue, @Param("pcValue") String pcValue, @Param("ypValue") String ypValue, @Param("jpbh") String jpbh, @Param("begin") String begin, @Param("end") String end);

    /**
     * 汇总查询-获取最大数目
     *
     * @param mkbh
     * @param bqValue
     * @param fsValue
     * @param pcValue
     * @param ypValue
     * @param jpbh
     * @param begin
     * @param end
     * @return
     */
    int getMaxCount(@Param("mkbh") String mkbh, @Param("bqValue") String bqValue, @Param("fsValue") String fsValue, @Param("pcValue") String pcValue, @Param("ypValue") String ypValue, @Param("jpbh") String jpbh, @Param("begin") String begin, @Param("end") String end);

    /**
     * 获取状态标志
     * @param jlxh 记录序号
     * @return
     */
    int getZtbz(@Param("jlxh") Long jlxh);
}
