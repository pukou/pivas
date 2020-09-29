package com.bsoft.mob.pivas.service.commons;

import com.bsoft.mob.datasource.DataSource;
import com.bsoft.mob.pivas.persistence.mob.DateTimeMapper;
import com.bsoft.mob.service.RouteDataSourceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * 提供数据库时间查询服务
 * Created by huangy on 2015/7/14.
 */
@Service
public class DateTimeService extends RouteDataSourceService {

    private static final Log logger = LogFactory.getLog(DateTimeService.class);

    @Autowired
    DateTimeMapper mapper;

    /**
     * 获取数据库当前时间
     *
     * @param dataSource 非空，要查询的数据库
     * @return 成功返回数据库当前时间，失败返回null
     */
    public String now(DataSource dataSource) {

        if (dataSource == null) {
            return null;
        }
        keepOrRoutingDateSource(dataSource);
        String dateTime = null;
        try {
            dateTime = mapper.now();
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return dateTime;
    }
}
