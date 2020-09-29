package com.bsoft.mob.pivas.service.biz;

import com.bsoft.mob.datasource.DataSource;
import com.bsoft.mob.pivas.pojo.biz.StatisAll;
import com.bsoft.mob.pivas.pojo.biz.StatisMy;
import com.bsoft.mob.pivas.service.biz.support.StatisService;
import com.bsoft.mob.service.BizResponse;
import com.bsoft.mob.service.RouteDataSourceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 提供统计查询有关服务
 * Created by huangy on 2015/6/15.
 */
@Service
public class StatisMTService extends RouteDataSourceService {

    private static final Log logger = LogFactory.getLog(StatisMTService.class);

    @Autowired
    StatisService statisService;


    /**
     * 个人工作量统计
     *
     * @param ygdm  员工编号
     * @param jpbh  静配编号
     * @param begin 开始时间
     * @param end   结束时间
     * @return 返回{@link BizResponse#datalist} ,datalist对象中包含 MKMC（模块名称）和MKSL（模块数量）两个字段
     */
    public BizResponse<StatisMy> statisMy(String ygdm, String jpbh, String begin, String end) {

        BizResponse<StatisMy> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            //查询要统计的模块编码
            List<Map<String, Object>> datalist = statisService.getGZTJs(jpbh);
            List<StatisMy> data = new ArrayList<>(datalist.size());
            for (Map<String, Object> mk : datalist) {
                BigDecimal mkbh = (BigDecimal) mk.get("MKBH");
                int count = statisService.getCount(mkbh.toString(), ygdm, jpbh, begin, end);

                //大于0才返回给客户端
                if (count > 0) {
                    StatisMy object = new StatisMy();
                    object.mkmc = (String) mk.get("MKMC");
                    object.mksl = count;
                    data.add(object);
                }

            }
            result.datalist = data;
            result.isSuccess = true;

        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;

    }

    /**
     * 汇总查询
     *
     * @param jpbh    静配编号
     * @param bqValue 病区ID
     * @param fsValue 配制方式
     * @param pcValue 批次
     * @param ypValue 主药类型
     * @param begin   开始时间
     * @param end     结束时间
     * @return
     */
    public BizResponse<StatisAll> statisAll(String jpbh, String bqValue, String fsValue, String pcValue, String ypValue, String begin, String end) {

        BizResponse<StatisAll> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            //查询要统计的模块编码
            List<Map<String, Object>> datalist = statisService.getGZTJs(jpbh);
            List<StatisAll> data = new ArrayList<>(datalist.size());

            for (Map<String, Object> mk : datalist) {
                BigDecimal mkbh = (BigDecimal) mk.get("MKBH");
                if(mkbh!=BigDecimal.valueOf(9)){
                    int maxCount = statisService.getMaxCount(mkbh.toString(), bqValue, fsValue, pcValue, ypValue, jpbh, begin, end);
                    int doneCount = statisService.getDoneCount(mkbh.toString(), bqValue, fsValue, pcValue, ypValue, jpbh, begin, end);

                    StatisAll object = new StatisAll();
                    object.mkmc = (String) mk.get("MKMC");
                    object.mksl = doneCount;
                    object.zsl = maxCount;
                    data.add(object);
                }
            }
            result.datalist = data;
            result.isSuccess = true;

        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }
}
