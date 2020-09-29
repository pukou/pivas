package com.bsoft.mob.pivas.service.biz.support;

import com.bsoft.mob.pivas.domain.mob.SF01;
import com.bsoft.mob.pivas.domain.mob.SF02;
import com.bsoft.mob.pivas.persistence.mob.SF01Mapper;
import com.bsoft.mob.pivas.persistence.mob.SF02Mapper;
import com.bsoft.mob.pivas.pojo.biz.ActionType;
import com.bsoft.mob.pivas.pojo.biz.TrankMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by huangy on 2015-04-23.
 */
@Service
public class SFService {

    @Autowired
    SF01Mapper sf01Mapper;

    @Autowired
    SF02Mapper sf02Mapper;


    public SF01 getSF01(String barcode, String jgid) throws SQLException {
        return sf01Mapper.getSF01(barcode, jgid);
    }

    @Transactional(rollbackFor = Exception.class)
    public int setTZBZ(long jlxh, int tzbz) throws SQLException {
        return sf01Mapper.setTZBZ(jlxh, tzbz);
    }

    public List<SF02> getSF02s(Long jlxh) throws SQLException {
        return sf02Mapper.getSF02s(jlxh);
    }

    @Transactional(rollbackFor = Exception.class)
    public int action(Long jlxh, String time, String ygdm, ActionType actionType, int ztbz) throws SQLException {

        switch (actionType) {
            case PLACE_DRUG:
                return sf01Mapper.actionPlace(jlxh, time, ygdm,ztbz);
            case PLACE_DRUG_CHECK:
                return sf01Mapper.actionPlaceCheck(jlxh, time, ygdm,ztbz);
            case BILLING:
                return sf01Mapper.actionBilling(jlxh, time, ygdm);
            case PRODUCT_CHECK:
                return sf01Mapper.actionProductCheck(jlxh, time, ygdm,ztbz);
        }
        return 0;
    }

    public List<SF01> getSF01s(String bq, String pc, String pzfs, String zylx, String fzcx, String begin, String end, String jgid, String jpbh, boolean all) throws SQLException {

        if (all) {
            return sf01Mapper.getSF01s(bq, pc, pzfs, zylx, fzcx, begin, end, jgid, jpbh);
        }
        return sf01Mapper.getTodos(bq, pc, pzfs, zylx, fzcx, begin, end, jgid, jpbh);
    }


    public List<SF01> getSF01sByDBTM(String dbtm) throws SQLException {
        return sf01Mapper.getSF01sByDBTM(dbtm);
    }

    public int sign(Long[] jlxhs, String time, String ygdm) throws SQLException {
        return sf01Mapper.actionSign(jlxhs, time, ygdm);
    }

    public int refuseSign(String txm, String yyxh, String time, String ygdm)throws SQLException {
        return sf01Mapper.refuseSign(txm, yyxh,time, ygdm);
    }

    public int getZtbz(Long jlxh)throws SQLException{
        return sf01Mapper.getZtbz(jlxh);
    }
}
