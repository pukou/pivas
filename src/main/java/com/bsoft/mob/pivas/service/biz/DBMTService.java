package com.bsoft.mob.pivas.service.biz;

import com.bsoft.mob.datasource.DataSource;
import com.bsoft.mob.pivas.domain.hrp.KSDM;
import com.bsoft.mob.pivas.domain.mob.DBJL;
import com.bsoft.mob.pivas.domain.mob.SF01;
import com.bsoft.mob.pivas.domain.portal.XTYH;
import com.bsoft.mob.pivas.pojo.biz.PkgMsg;
import com.bsoft.mob.pivas.service.biz.support.DBService;
import com.bsoft.mob.pivas.service.biz.support.SFService;
import com.bsoft.mob.pivas.service.commons.support.OfficeService;
import com.bsoft.mob.pivas.service.security.support.SecurityService;
import com.bsoft.mob.service.BizResponse;
import com.bsoft.mob.service.RouteDataSourceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * 提供打包相关服务
 * Created by huangy on 2015/6/10.
 */
@Service
public class DBMTService extends RouteDataSourceService {

    private static final Log logger = LogFactory.getLog(DBMTService.class);

    @Autowired
    DBService dbService;

    @Autowired
    OfficeService officeService;

    @Autowired
    SecurityService securityService;


    @Autowired
    SFService sfService;

    /**
     * 根据打包条码，获取打包信息
     *
     * @param dbtm
     * @return
     */
    public BizResponse<PkgMsg> getPkgMsg(String dbtm) {

        BizResponse<PkgMsg> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            DBJL dbjl = dbService.getDBJL(dbtm);
            if (dbjl == null) {
                result.isSuccess = false;
                result.errorMessage = "未查询到打包数据";
                return result;
            }
            //获取病区名称
            keepOrRoutingDateSource(DataSource.HRP);
            List<KSDM> ksdms = officeService.getOffices(dbjl.BRBQ);
            if (ksdms != null && !ksdms.isEmpty()) {
                dbjl.BQMC = ksdms.get(0).KSMC;
            }

            //获取打包人姓名
            keepOrRoutingDateSource(DataSource.PORTAL);
            XTYH xtyh = securityService.getXTYH(dbjl.DBGH);
            if (xtyh != null) {
                dbjl.DBRY = xtyh.YHXM;
            }

            // 获取SF01列表
            keepOrRoutingDateSource(DataSource.MOB);
            List<SF01> sf01List = sfService.getSF01sByDBTM(dbtm);
            PkgMsg pkgMsg = new PkgMsg();
            pkgMsg.dbjl = dbjl;
            pkgMsg.sf01List = sf01List;
            result.isSuccess = true;
            result.data = pkgMsg;

        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }
}
