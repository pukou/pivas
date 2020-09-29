package com.bsoft.mob.pivas.service.security.support;

import com.bsoft.mob.cache.LoginUser;
import com.bsoft.mob.domain.UserParams;
import com.bsoft.mob.pivas.controller.barcode.BarcodeController;
import com.bsoft.mob.pivas.domain.hrp.HSQL;
import com.bsoft.mob.pivas.domain.mob.QXKZ;
import com.bsoft.mob.pivas.domain.mob.YHTM;
import com.bsoft.mob.pivas.domain.portal.XTYH;
import com.bsoft.mob.pivas.persistence.hrp.HSQLMapper;
import com.bsoft.mob.pivas.persistence.mob.ParmMapper;
import com.bsoft.mob.pivas.persistence.mob.QXKZMapper;
import com.bsoft.mob.pivas.persistence.mob.QYBQMapper;
import com.bsoft.mob.pivas.persistence.mob.YHTMMapper;
import com.bsoft.mob.pivas.persistence.portal.XTYHMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangy on 2015-04-14.
 */
@Service
public class SecurityService {

    @Autowired
    XTYHMapper xtyhMapper;

    @Autowired
    QXKZMapper qxkzMapper;

    @Autowired
    HSQLMapper hsqlMapper;

    @Autowired
    YHTMMapper yhtmMapper;

    @Autowired
    QYBQMapper  qybqMapper;

    @Autowired
    ParmMapper parmMapper;

    public XTYH login(LoginUser user) throws SQLException {
        return xtyhMapper.login(user);
    }


    public XTYH getUser(String username, String jgid) throws SQLException {
        return xtyhMapper.getUser(username, jgid);
    }


    public List<QXKZ> getCenters(String ygdm, String jgid) throws SQLException {
        return qxkzMapper.getAuthories(ygdm, jgid);
    }


    public List<HSQL> getOffices(String ygdm, String jgid) throws SQLException {
        return hsqlMapper.getAuthories(ygdm, jgid);
    }


    public YHTM getYHTM(String barcode) throws SQLException {
        return yhtmMapper.getYHTM(barcode);
    }


    public XTYH getUserByYGDM(String ygdm) throws SQLException {
        return xtyhMapper.getUserByYGDM(ygdm);
    }

    public List<HSQL> filterOffices(List<HSQL> bqs) throws SQLException {

        if (CollectionUtils.isEmpty(bqs)) {
            return bqs;
        }
        List<HSQL> result = new ArrayList<>(bqs.size());
        for (HSQL hsql:bqs){
            //查询静配编号，并将其录入HSQL中
            String jpbh = qybqMapper.getJPBH(hsql.KSDM);
            if(!StringUtils.isEmpty(jpbh)){
                hsql.JPBH=jpbh;
                result.add(hsql);
            }
        }
        return result;
    }

    public XTYH getXTYH(String ygbh) throws SQLException{
        return xtyhMapper.getXTYH(ygbh);
    }

    public List<UserParams> getJpzxParm(String gsjb, String gsdx, String[] csmcs, String jgid) throws SQLException{
        return parmMapper.getJpzxParm(gsjb,gsdx,csmcs,jgid);
    }
}
