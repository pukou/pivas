package com.bsoft.mob.pivas.service.system.support;

import com.bsoft.mob.domain.UserParams;
import com.bsoft.mob.pivas.domain.hrp.KSDM;
import com.bsoft.mob.pivas.domain.mob.JPLC;
import com.bsoft.mob.pivas.domain.mob.THYY;
import com.bsoft.mob.pivas.domain.mob.ZLSJ;
import com.bsoft.mob.pivas.persistence.hrp.KSDMMapper;
import com.bsoft.mob.pivas.persistence.hrp.MKIDMapper;
import com.bsoft.mob.pivas.persistence.mob.*;
import com.bsoft.mob.pivas.pojo.security.BarCodePrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by huangy on 2015-04-16.
 */
@Service
public class SystemService {

    @Autowired
    JPLCMapper jplcMapper;

    @Autowired
    TXMMapper txmMapper;

    @Autowired
    QYBQMapper qybqMapper;

    @Autowired
    KSDMMapper ksdmMapper;

    @Autowired
    ZLSJMapper zlsjMapper;

    @Autowired
    THYYMapper thyyMapper;

    @Autowired
    MKIDMapper mkidMapper;

    public List<String> getPDAWorkFlow(String jgid, String jpid, String ygdm) throws SQLException {
        return jplcMapper.getPDAWorkFlow(jgid, jpid, ygdm);
    }

    public List<String> getMKIDByYGDM(String jgid, String ygdm) throws SQLException {
        return mkidMapper.getMKIDByYGDM(jgid, ygdm);
    }


    public BarCodePrefix getBarCodePrefixs(String jgid, String jpid) throws SQLException {
        return txmMapper.getBarCodePrefixs(jgid, jpid);
    }

    public String[] getOfficeIds(String jpbh) throws SQLException {
        return qybqMapper.getOffices(jpbh);
    }

    public List<KSDM> getOffices(String[] bqids) throws SQLException {
        return ksdmMapper.getOffices(bqids);
    }

    public List<ZLSJ> getPCs(String jpbh) throws SQLException {
        return zlsjMapper.getPCs(jpbh);
    }

    public JPLC getWorkFlow(String jpbh, int mkbh) throws SQLException {
        return jplcMapper.getWorkFlow(jpbh, mkbh);
    }

    public List<THYY> getTHYYs(String thlx,String jgid) throws SQLException{
        return thyyMapper.getTHYYs(thlx,jgid);
    }

    public List<String> getYYNRs(String thlx,String jgid) throws SQLException{
        return thyyMapper.getYYNRs(thlx,jgid);
    }


}
