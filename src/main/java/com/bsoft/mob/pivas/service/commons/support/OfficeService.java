package com.bsoft.mob.pivas.service.commons.support;

import com.bsoft.mob.pivas.domain.hrp.KSDM;
import com.bsoft.mob.pivas.persistence.hrp.KSDMMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by huangy on 2015/6/10.
 */
@Service
public class OfficeService {

    @Autowired
    KSDMMapper ksdmMapper;

    public List<KSDM> getOffices(String... bqids) throws SQLException{
        return ksdmMapper.getOffices(bqids);
    }

    public List<KSDM> getAllOffices() throws  SQLException{
        return ksdmMapper.getAllOffices();
    }
}
