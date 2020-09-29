package com.bsoft.mob.pivas.service.commons.support;

import com.bsoft.mob.pivas.domain.mob.TZGZ;
import com.bsoft.mob.pivas.persistence.hrp.BQYZMapper;
import com.bsoft.mob.pivas.persistence.mob.TZGZMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by huangy on 2015-04-22.
 */
@Service
public class OrderService {

    @Autowired
    TZGZMapper tzgzMapper;

    @Autowired
    BQYZMapper bqyzMapper;


    /**
     * 在V_MOB_HIS_BQYZ中获取停嘱时间（TZSJ）
     *
     * @param yzzh
     * @return
     */
    public String getTZSJ(String yzzh) throws SQLException {
        return bqyzMapper.getTZSJ(yzzh);
    }

    public TZGZ getTZGZ(String ksdm) throws SQLException{
        return tzgzMapper.getTZGZ(ksdm);
    }

}
