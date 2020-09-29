package com.bsoft.mob.pivas.service.commons;

import com.bsoft.mob.datasource.DataSource;
import com.bsoft.mob.pivas.domain.hrp.KSDM;
import com.bsoft.mob.pivas.persistence.hrp.KSDMMapper;
import com.bsoft.mob.pivas.service.commons.support.OfficeService;
import com.bsoft.mob.service.BizResponse;
import com.bsoft.mob.service.RouteDataSourceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * 提供科室有关服务
 * Created by huangy on 2015/6/10.
 */
@Service
public class OfficeMTService extends RouteDataSourceService {


    private static final Log logger = LogFactory.getLog(OfficeMTService.class);

    @Autowired
    OfficeService officeService;
    @Autowired
    KSDMMapper ksdmMapper;

    /**
     * 获取科室信息
     *
     * @param bqids 病区ID数据， 不能为NULL
     * @return
     */
    public BizResponse<KSDM> getOffices(String... bqids) {

        BizResponse<KSDM> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.HRP);
        try {
            List<KSDM> datalist = officeService.getOffices(bqids);
            result.isSuccess = true;
            result.datalist = datalist;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }

    public BizResponse<KSDM> getAllOffices() {

        BizResponse<KSDM> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.HRP);
        try {
            List<KSDM> datalist = officeService.getAllOffices();
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
