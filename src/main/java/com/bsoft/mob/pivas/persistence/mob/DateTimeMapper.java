package com.bsoft.mob.pivas.persistence.mob;

import java.sql.SQLException;

/**
 * 用于获取系统时间
 * Created by huangy on 2015/7/14.
 */
public interface DateTimeMapper {

    String now() throws SQLException;
}
