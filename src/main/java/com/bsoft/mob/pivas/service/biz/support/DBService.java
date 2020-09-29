package com.bsoft.mob.pivas.service.biz.support;

import com.bsoft.mob.pivas.domain.mob.DBJL;
import com.bsoft.mob.pivas.persistence.mob.DBJLMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by huangy on 2015/6/10.
 */
@Service
public class DBService {

    @Autowired
    DBJLMapper dbjlMapper;

    public DBJL getDBJL(String dbtm) throws SQLException{
        return dbjlMapper.getDBJL(dbtm);
    }
}
