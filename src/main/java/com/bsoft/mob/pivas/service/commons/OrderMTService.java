package com.bsoft.mob.pivas.service.commons;

import com.bsoft.mob.datasource.DataSource;
import com.bsoft.mob.pivas.domain.mob.TZGZ;
import com.bsoft.mob.pivas.service.commons.support.OrderService;
import com.bsoft.mob.service.BizResponse;
import com.bsoft.mob.service.RouteDataSourceService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * 提供医嘱有关服务
 * Created by huangy on 2015-04-22.
 */
@Service
public class OrderMTService extends RouteDataSourceService {

    @Autowired
    OrderService orderService;


    public static final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");


    private static final Log logger = LogFactory.getLog(OrderMTService.class);


    /**
     * 判断医嘱是否停嘱
     *
     * @param ksdm 科室代码
     * @param tzsj 停嘱时间
     * @param zlsj 治疗时间（用药时间）
     * @return
     */
    public BizResponse<Boolean> isOrderStop(String ksdm, String tzsj, String zlsj) {


        BizResponse<Boolean> result = new BizResponse<>();
        try {
            //tzsj为null,表示未停嘱
            if (StringUtils.isEmpty(tzsj)) {
                result.isSuccess = true;
                result.data = false;
                return result;
            }

            keepOrRoutingDateSource(DataSource.MOB);
            //获取停嘱判断规则
            TZGZ tzgz = orderService.getTZGZ(ksdm);
            if (tzgz == null) {
                result.isSuccess = false;
                result.errorMessage = "当前病区未配制停嘱规则";
                return result;
            }

            switch (tzgz.GZLX) {


                case 1://是否停嘱判断
                    //只要TZSJ不为NULL，就表示停嘱
                    result.isSuccess = false;
                    result.data = true;
                    break;
                case 2: //停嘱时间点判断
                    //当前时间点 和  GZSJ比较 ；小于为不停嘱；
                    LocalTime now = LocalTime.now();
                    LocalTime gzsj = LocalTime.parse(tzgz.GZSJ, timeFormatter);
                    result.isSuccess = true;
                    result.data = now.isAfter(gzsj);
                    break;
                case 3:
                    // SF01中的ZLSJ，和GZSJ 比较；小于为不停嘱
                    LocalTime zlsjTime = LocalTime.parse(zlsj, timeFormatter);
                    gzsj = LocalTime.parse(tzgz.GZSJ, timeFormatter);
                    result.isSuccess = true;
                    result.data = zlsjTime.isAfter(gzsj);
                    break;
                case 4:
                    // 结合用药和停嘱时间；TZSJ 和SF01中的ZLSJ 比较
                    zlsjTime = LocalTime.parse(zlsj, timeFormatter);
                    LocalTime tzsjTime = LocalTime.parse(tzsj, timeFormatter);
                    result.isSuccess = true;
                    result.data = zlsjTime.isAfter(tzsjTime);
                    break;
                default:
                    //默认为未停嘱
                    result.isSuccess = true;
                    result.data = false;

            }
            return result;

        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }


    /**
     * 根据医嘱组号，获取停嘱时间
     *
     * @param yzzh 医嘱组号
     * @return
     */
    public BizResponse<String> getTZSJ(String yzzh) {

        BizResponse<String> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.HRP);
        try {
            String tzsj = orderService.getTZSJ(yzzh);
            result.isSuccess = true;
            result.data = tzsj;
            return result;

        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }



}
