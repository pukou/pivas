package com.bsoft.mob.pivas.service.system;

import com.bsoft.mob.datasource.DataSource;
import com.bsoft.mob.domain.UserParams;
import com.bsoft.mob.pivas.domain.hrp.KSDM;
import com.bsoft.mob.pivas.domain.mob.JPLC;
import com.bsoft.mob.pivas.domain.mob.THYY;
import com.bsoft.mob.pivas.domain.mob.ZLSJ;
import com.bsoft.mob.pivas.pojo.security.BarCodePrefix;
import com.bsoft.mob.pivas.pojo.security.UseConfig;
import com.bsoft.mob.pivas.pojo.security.WorkFlow;
import com.bsoft.mob.pivas.service.security.support.SecurityService;
import com.bsoft.mob.pivas.service.system.support.SystemService;
import com.bsoft.mob.service.BizResponse;
import com.bsoft.mob.service.RouteDataSourceService;
import com.bsoft.mob.service.params.UserParamsMainService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提供系统参数等查询功能
 * Created by huangy on 2015-04-16.
 */
@Service
public class SystemMTService extends RouteDataSourceService {

    private static final Log logger = LogFactory.getLog(SystemMTService.class);


    @Autowired
    SystemService systemService;

    @Autowired
    UserParamsMainService userParamsService;

    @Autowired
    SecurityService securityService;


    /**
     * 获取PDA启用的工作流程
     *
     * @param jgid  机构ID
     * @param ygdm 用户id
     * @return
     */
    public BizResponse<WorkFlow> getMKIDByYGDM(String jgid, String ygdm) {

        BizResponse<WorkFlow> result = new BizResponse<>();
//        if (ArrayUtils.isEmpty(jpids)) {
//            result.isSuccess = false;
//            result.errorMessage = "静配中心数据列表为空";
//            return result;
//        }
        keepOrRoutingDateSource(DataSource.HRP);
        try {
            //List<WorkFlow> datalist = new ArrayList<>(jpids.length);
            List<WorkFlow> datalist = new ArrayList<>();
//            for (String jpid : jpids) {
                List<String> mkids = systemService.getMKIDByYGDM(jgid,ygdm);
//                WorkFlow workFlow = new WorkFlow(jpid, mkids);
                WorkFlow workFlow = new WorkFlow( mkids);
                datalist.add(workFlow);
//            }
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
     * 获取用户参数
     *
     * @param jgid  机构ID
     * @param jpids 静配ID数组
     * @return
     */
    public BizResponse<UseConfig> getUseConfigs(String jgid, String... jpids) {
        keepOrRoutingDateSource(DataSource.MOB);
        BizResponse<UseConfig> result = new BizResponse<>();

        if (ArrayUtils.isEmpty(jpids)) {
            result.isSuccess = false;
            result.errorMessage = "静配中心数据列表为空";
            return result;
        }

        List<UseConfig> datalist = new ArrayList(jpids.length);
        for (String id : jpids) {

            UseConfig uc = new UseConfig();

            String[] csmcs = {"JM_BYZDQR", "JM_BYHDZDQR", "JM_CPHDZDQR", "JM_BYMS", "JM_QSMS"};
            //获取配制中心参数列表
//            BizResponse<UserParams> biz = userParamsService.getUserParamsList("2", id, csmcs, jgid, DataSource.MOB);
            try {
                List<UserParams> userParamslist = securityService.getJpzxParm("2", id, csmcs, jgid);

//                if (userParamslist.size() <= 0) {
//                    result.isSuccess = false;
//                    result.errorMessage = "获取静配中心参数失败";
//                    return result;
//                }

                for (UserParams userParams : userParamslist) {
                    String csmc = userParams.CSMC;
                    String csqk = userParams.CSQZ;
                    if ("JM_BYZDQR".equals(csmc)) {
                        //获取摆药扫描自动参数
                        uc.BYZDQR = csqk;
                    } else if ("JM_BYHDZDQR".equals(csmc)) {
                        //获取摆药核对扫描自动参数
                        uc.BYHDZDQR = csqk;
                    } else if ("JM_CPHDZDQR".equals(csmc)) {
                        //获取成品核对扫描自动确认参数
                        uc.CPHDZDQR = csqk;
                    } else if ("JM_BYMS".equals(csmc)) {
                        //获取摆药模式参数
                        uc.BYMS = csqk;
                    } else if ("JM_QSMS".equals(csmc)) {
                        //获取病区签收模式参数
                        uc.BQQSMS = csqk;
                    }
                }

                uc.JPBH = id;
                if (!datalist.contains(uc)) {datalist.add(uc);

                }

                result.isSuccess = true;
                result.datalist = datalist;
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                result.isSuccess = false;
                result.errorMessage = "获取静配中心参数失败";
            }
        }
        return result;
    }


    /**
     * 获取条码前缀
     *
     * @param jgid  机构ID
     * @param jpids 静配ID数组
     * @return
     */
    public BizResponse<BarCodePrefix> getBarCodePrefixs(String jgid, String... jpids) {

        BizResponse<BarCodePrefix> result = new BizResponse<>();
        if (ArrayUtils.isEmpty(jpids)) {
            result.isSuccess = false;
            result.errorMessage = "静配中心数据列表为空";
            return result;
        }
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            List<BarCodePrefix> datalist = new ArrayList(jpids.length);

            //获取胸卡前缀
            BizResponse biz = userParamsService.getUserParams("1", "JM", "JM_XKQZ", jgid, DataSource.MOB);
            if (!biz.isSuccess) {
                return biz;
            }
            String xkQz = "XK";
            if (!CollectionUtils.isEmpty(biz.datalist)) {
                xkQz = (String) biz.datalist.get(0);
            }

            for (String jpid : jpids) {
                //获取相关条码前缀
                BarCodePrefix prefixs = systemService.getBarCodePrefixs(jgid, jpid);
                if (prefixs != null) {
                    prefixs.xkQz = xkQz;
                    datalist.add(prefixs);
                }
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


    /**
     * 获取当前静配中心启用的病区列表
     *
     * @param jpbh
     * @return {@link BizResponse#data} 含病区列表
     */
    public BizResponse<Map<String, String>> getOffices(String jpbh) {


        BizResponse<Map<String, String>> result = new BizResponse<>();
        if (StringUtils.isEmpty(jpbh)) {
            result.isSuccess = false;
            result.errorMessage = "静配中心数据为空";
            return result;
        }

        keepOrRoutingDateSource(DataSource.MOB);
        try {
            String[] bqids = systemService.getOfficeIds(jpbh);
            if (ArrayUtils.isEmpty(bqids)) {
                result.isSuccess = false;
                result.errorMessage = "未查找到启用病区数据";
                return result;
            }

            keepOrRoutingDateSource(DataSource.HRP);
            List<KSDM> datalist = systemService.getOffices(bqids);

            Map<String, String> data = new HashMap<>();
            for (KSDM ksdm : datalist) {
                data.put(ksdm.KSDM, ksdm.KSMC);
            }
            result.isSuccess = true;
            result.data = data;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }

    /**
     * 获取批次信息
     *
     * @param jpbh
     * @return
     */
    public BizResponse<Map<String, String>> getPCs(String jpbh) {

        BizResponse<Map<String, String>> result = new BizResponse<>();
        if (StringUtils.isEmpty(jpbh)) {
            result.isSuccess = false;
            result.errorMessage = "静配中心数据为空";
            return result;
        }

        keepOrRoutingDateSource(DataSource.MOB);
        try {

            List<ZLSJ> datalist = systemService.getPCs(jpbh);
            Map<String, String> data = new HashMap<>();
            for (ZLSJ zlsj : datalist) {
                data.put(zlsj.JLXH, zlsj.PCID);
            }
            result.isSuccess = true;
            result.data = data;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }

    /**
     * 获取静配流程
     *
     * @param jpbh 静配编号
     * @param mkbh 模块编号
     * @return
     */
    public BizResponse<JPLC> getWorkFlow(String jpbh, int mkbh) {


        BizResponse<JPLC> result = new BizResponse<>();
        if (StringUtils.isEmpty(jpbh)) {
            result.isSuccess = false;
            result.errorMessage = "静配中心数据为空";
            return result;
        }
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            JPLC data = systemService.getWorkFlow(jpbh, mkbh);
            if (data == null) {
                result.isSuccess = false;
                result.errorMessage = "未查询到流程数据或该流程未启动";
                return result;
            }
            result.isSuccess = true;
            result.data = data;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }


    /**
     * 获取退回原因列表
     *
     * @param thlx 退回类型
     * @param jgid 机构ID
     * @return
     */
    public BizResponse<THYY> getTHYYs(String thlx, String jgid) {
        BizResponse<THYY> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            List<THYY> datalist = systemService.getTHYYs(thlx, jgid);
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
