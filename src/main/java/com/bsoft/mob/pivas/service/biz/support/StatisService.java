package com.bsoft.mob.pivas.service.biz.support;

import com.bsoft.mob.pivas.domain.mob.JPLC;
import com.bsoft.mob.pivas.persistence.mob.JPLCMapper;
import com.bsoft.mob.pivas.persistence.mob.SF01Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by huangy on 2015/6/15.
 */
@Service
public class StatisService {

    @Autowired
    JPLCMapper jplcMapper;

    @Autowired
    SF01Mapper sf01Mapper;

    public List<Map<String,Object>> getGZTJs(String jpbh) throws SQLException {
        return jplcMapper.getTJWorkFlow(jpbh);
    }

    public int getCount(String mkbh, String ygdm, String jpbh, String begin, String end) {
        return sf01Mapper.getCount(mkbh, ygdm, jpbh, begin, end);
    }


    public int getMaxCount(String mkbh, String bqValue, String fsValue, String pcValue, String ypValue, String jpbh, String begin, String end) {
        return sf01Mapper.getMaxCount(mkbh, bqValue, fsValue, pcValue, ypValue, jpbh, begin, end);
    }

    public int getDoneCount(String mkbh, String bqValue, String fsValue, String pcValue, String ypValue, String jpbh, String begin, String end) {
        return sf01Mapper.getDoneCount(mkbh, bqValue, fsValue,pcValue, ypValue,jpbh,begin, end);
    }
}
