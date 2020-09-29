package com.bsoft.mob.pivas.pojo.security;

import com.bsoft.mob.pivas.domain.hrp.HSQL;
import com.bsoft.mob.pivas.domain.mob.QXKZ;
import com.bsoft.mob.pivas.domain.portal.XTYH;

import java.util.List;

/**
 * 登陆响应正文
 * Created by huangy on 2015-04-15.
 */
public class LoginResponse {

    /**
     * 用户Token
     */
    public String token;

    /**
     * 静配药房列表
     */
    public List<QXKZ> jpzxs;

    /**
     * 病区列表
     */
    public List<HSQL> bqs;

    /**
     * 所有启动的静配模块列表
     */
    public List<WorkFlow> jplcs;

    /**
     * 登陆用户信息
     */
    public XTYH xtyh;

    /**
     * 用户参数列表
     */
    public List<UseConfig> ucs;


    /**
     * 条码前缀对象
     */
    public List<BarCodePrefix> prefixs;

    /*
     * 当前时间
     */
    public String now;
}
