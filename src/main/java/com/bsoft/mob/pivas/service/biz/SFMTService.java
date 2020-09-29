package com.bsoft.mob.pivas.service.biz;

import com.bsoft.mob.datasource.DataSource;
import com.bsoft.mob.pivas.domain.mob.SF01;
import com.bsoft.mob.pivas.domain.mob.SF02;
import com.bsoft.mob.pivas.pojo.Response;
import com.bsoft.mob.pivas.pojo.biz.ActionType;
import com.bsoft.mob.pivas.pojo.biz.TrankMsg;
import com.bsoft.mob.pivas.service.PublicService;
import com.bsoft.mob.pivas.service.biz.support.SFService;
import com.bsoft.mob.pivas.service.commons.OrderMTService;
import com.bsoft.mob.pivas.service.security.support.SecurityService;
import com.bsoft.mob.service.BizResponse;
import com.bsoft.mob.service.RouteDataSourceService;
import net.spy.memcached.MemcachedClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 提供审方相关服务接口
 * Created by huangy on 2015-04-22.
 */
@Service
public class SFMTService extends RouteDataSourceService {

    @Autowired
    SFService sfService;

    @Autowired
    OrderMTService orderMTService;

    @Autowired
    SecurityService securityService;

    @Autowired
    PublicService publicService;

    private static final Log logger = LogFactory.getLog(SFMTService.class);

    /**
     * 根据医嘱条码获取审方数据
     *
     * @param barcode
     * @return
     */
    public BizResponse<SF01> getSF01(String barcode, String jgid) {

        BizResponse<SF01> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            SF01 sf01 = sfService.getSF01(barcode, jgid);
            if (sf01 == null) {
                result.isSuccess = false;
                result.errorMessage = "医嘱数据不存在";
                return result;
            }
            sf01.BQMC = publicService.getWardName(sf01.BRBQ);
            result.data = sf01;
            result.isSuccess = true;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }


    /**
     * 医嘱停嘱
     *
     * @param JLXH   SF01 记录序号
     * @param toStop true为停嘱；false为不停嘱
     * @return
     */
    public BizResponse<Boolean> setTZBZ(long JLXH, boolean toStop) {


        BizResponse<Boolean> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            sfService.setTZBZ(JLXH, toStop ? 1 : 0);
            result.isSuccess = true;
            return result;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }

    public BizResponse<SF02> getSF02s(Long jlxh) {

        BizResponse<SF02> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            List<SF02> datalist = sfService.getSF02s(jlxh);
            result.isSuccess = true;
            result.datalist = datalist;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }


    /**
     * 检测审方是否已停嘱，如果已停嘱，同时更新SF01表
     *
     * @param txm  医嘱条形码
     * @param jgid 机构ID
     * @return 未停嘱，{@link Response#data}返回SF01 ,已停嘱{@link Response#data}返回null
     */
    public Response<SF01> checkSFIfStopped(String txm, String jgid) {

        Response<SF01> response = new Response<>();

        BizResponse<SF01> result = getSF01(txm, jgid);
        if (!result.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = result.errorMessage;
            return response;
        }

        SF01 sf01 = result.data;

        if (sf01.THBZ == 1) {
            response.isSuccess = false;
            response.errorMessage = "医嘱已退回";
            response.errorflag = 1;
            return response;
        }

        if (sf01.TZBZ == 1) {
            response.isSuccess = false;
            response.errorMessage = "医嘱已停嘱";
            response.errorflag = 2;
            return response;
        }

        //获取停嘱时间
        BizResponse<String> bizResponse = orderMTService.getTZSJ(sf01.PZZH);
        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }

        //停嘱时间
        String tzsj = bizResponse.data;

        //判断是否停嘱
        BizResponse<Boolean> bizResponse2 = orderMTService.isOrderStop(sf01.BRBQ, tzsj, sf01.ZLSJ);
        if (!bizResponse2.isSuccess) {

            response.isSuccess = false;
            response.errorMessage = bizResponse2.errorMessage;
            return response;
        }

        boolean isStopped = (boolean) bizResponse2.data;
        if (isStopped) {
            setTZBZ(sf01.JLXH, true);
            response.isSuccess = false;
            response.errorMessage = "医嘱已停嘱";
            response.errorflag = 2;
            return response;
        }

        response.data = sf01;
        response.isSuccess = true;
        return response;

    }

    /**
     * 执行摆药,摆药核对，计费，成品核对
     *
     * @param jlxh      SF01 记录序号
     * @param time      执行时间
     * @param ygdm      执行工号
     * @param actionType
     * @return
     */
    public BizResponse<Integer> action(Long jlxh, String time, String ygdm, ActionType actionType,int ztbz) {

        BizResponse<Integer> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            int data = sfService.action(jlxh, time, ygdm, actionType,ztbz);
            if (data == 0) {
                result.isSuccess = false;
                result.errorMessage = "序号为" + jlxh + "审方数据不存在或操作已被执行";
            } else {
                result.isSuccess = true;
                result.data = data;
            }
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }

    /**
     * 执行签收
     *
     * @param jlxhs 打包条码
     * @param time  执行时间
     * @param ygdm  执行工号
     * @return 受影响的行数
     */
    public Response<Integer> sign(Long[] jlxhs, String time, String ygdm) {

        Response<Integer> result = new Response<>();
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            int data = sfService.sign(jlxhs, time, ygdm);
            if (data == 0) {
                result.isSuccess = false;
                result.errorflag = 5;
                result.errorMessage = "签收条码不存在或已被签收";
            } else {
                result.isSuccess = true;
                result.data = data;
            }
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }


    /**
     * @param bq
     * @param pc
     * @param pzfs
     * @param zylx
     * @param fzcx
     * @param begin
     * @param end
     * @param jgid
     * @param all
     * @return
     */
    public BizResponse<SF01> getSF01s(String bq, String pc, String pzfs, String zylx, String fzcx, String begin, String end, String jgid, String jpbh, boolean all) {

        BizResponse<SF01> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            if (!StringUtils.isEmpty(fzcx)) {
                switch (fzcx) {
                    case "3":
                        fzcx = "BYBZ";
                        break;
                    case "4":
                        fzcx = "BYHD";
                        break;
                    case "5":
                        fzcx = "FYZT";
                        break;
                    case "6":
                        fzcx = "CPHD";
                        break;
                }
            }
            List<SF01> datalist = sfService.getSF01s(bq, pc, pzfs, zylx, fzcx, begin, end, jgid, jpbh, all);
            for(SF01 sf : datalist){
                sf.BQMC=publicService.getWardName(sf.BRBQ);
            }
            result.datalist = datalist;
            result.isSuccess = true;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }


    /**
     * 拒绝签收
     *
     * @param txm  条形码
     * @param yyxh 原因序号
     * @param time 时间
     * @param ygdm 员工代码
     * @return
     */
    public BizResponse<Integer> refuseSign(String txm, String yyxh, String time, String ygdm) {

        BizResponse<Integer> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            int data = sfService.refuseSign(txm, yyxh, time, ygdm);
            if (data == 0) {
                result.isSuccess = false;
                result.errorMessage = "条形码为" + txm + "审方数据不存在或已被签收";
            } else {
                result.isSuccess = true;
                result.data = data;
            }
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }

    /**
     * 查询标签执行过程
     *
     * @param txm
     * @param jgid
     * @return
     */
    public BizResponse<TrankMsg> trank(String txm, String jgid) {

        BizResponse<TrankMsg> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.MOB);
        ArrayList<TrankMsg> datalist = new ArrayList<>(9);
        try {
            SF01 sf01 = sfService.getSF01(txm, jgid);
            if (sf01 == null) {
                result.isSuccess = false;
                result.errorMessage = "医嘱数据不存在";
                return result;
            }

            keepOrRoutingDateSource(DataSource.PORTAL);

            //获取审方处理
            if (!StringUtils.isEmpty(sf01.SHGH)) {
                TrankMsg trankMsg = new TrankMsg();
                trankMsg.mkmc = "审方处理";
                trankMsg.sj = sf01.SHSJ;
                trankMsg.ygxm = securityService.getXTYH(sf01.SHGH).YHXM;
                datalist.add(trankMsg);
            }

            //获取标签打印
            if (sf01.DYCS != null && sf01.DYCS > 0) {
                TrankMsg trankMsg = new TrankMsg();
                trankMsg.mkmc = "标签打印";
                trankMsg.sj = sf01.DYSJ;
                trankMsg.ygxm = securityService.getXTYH(sf01.DYGH).YHXM;
                datalist.add(trankMsg);
            }

            //获取按签摆药
            if (sf01.BYBZ != null && sf01.BYBZ == 1) {
                TrankMsg trankMsg = new TrankMsg();
                trankMsg.mkmc = "按签摆药";
                trankMsg.sj = sf01.BYSJ;
                trankMsg.ygxm = securityService.getXTYH(sf01.BYGH).YHXM;
                datalist.add(trankMsg);
            }

            //获取摆药核对
            if (sf01.BYHD != null && sf01.BYHD == 1) {
                TrankMsg trankMsg = new TrankMsg();
                trankMsg.mkmc = "摆药核对";
                trankMsg.sj = sf01.BYHDSJ;
                trankMsg.ygxm = securityService.getXTYH(sf01.BYHDGH).YHXM;
                datalist.add(trankMsg);
            }

            //获取发药计费
            if (sf01.FYZT != null && sf01.FYZT == 1) {
                TrankMsg trankMsg = new TrankMsg();
                trankMsg.mkmc = "发药计费";
                trankMsg.sj = sf01.FYRQ;
                trankMsg.ygxm = securityService.getXTYH(sf01.FYGH).YHXM;
                datalist.add(trankMsg);
            }

            //获取成品核对
            if (sf01.CPHD != null && sf01.CPHD == 1) {
                TrankMsg trankMsg = new TrankMsg();
                trankMsg.mkmc = "成品核对";
                trankMsg.sj = sf01.CPHDSJ;
                trankMsg.ygxm = securityService.getXTYH(sf01.CPHDGH).YHXM;
                datalist.add(trankMsg);
            }

            //获取打包装箱
            if (sf01.DBHD != null && sf01.DBHD == 1) {
                TrankMsg trankMsg = new TrankMsg();
                trankMsg.mkmc = "打包装箱";
                trankMsg.sj = sf01.DBHDSJ;
                trankMsg.ygxm = securityService.getXTYH(sf01.DBHDGH).YHXM;
                datalist.add(trankMsg);
            }

            //获取病区签收
            if (sf01.BQQS != null && sf01.BQQS > 0) {
                TrankMsg trankMsg = new TrankMsg();
                if (1 == sf01.BQQS) {
                    trankMsg.mkmc = "病区签收";
                } else if (2 == sf01.BQQS) {
                    trankMsg.mkmc = "病区拒签";
                }
                trankMsg.sj = sf01.QSSJ;
                trankMsg.ygxm = securityService.getXTYH(sf01.QSGH).YHXM;
                datalist.add(trankMsg);
            }

            //获取退药处理
            if (sf01.TYZT != null && sf01.TYZT == 1) {
                TrankMsg trankMsg = new TrankMsg();
                trankMsg.mkmc = "退药处理";
                trankMsg.sj = sf01.TYRQ;
                trankMsg.ygxm = securityService.getXTYH(sf01.TYGH).YHXM;
                datalist.add(trankMsg);
            }

            result.isSuccess = true;
            result.datalist = datalist;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }

}
