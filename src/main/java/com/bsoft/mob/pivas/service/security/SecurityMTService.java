package com.bsoft.mob.pivas.service.security;

import com.bsoft.mob.cache.LoginUser;
import com.bsoft.mob.datasource.DataSource;
import com.bsoft.mob.pivas.domain.hrp.HSQL;
import com.bsoft.mob.pivas.domain.mob.QXKZ;
import com.bsoft.mob.pivas.domain.mob.YHTM;
import com.bsoft.mob.pivas.domain.portal.XTYH;
import com.bsoft.mob.pivas.service.security.support.SecurityService;
import com.bsoft.mob.service.BizResponse;
import com.bsoft.mob.service.RouteDataSourceService;
import com.bsoft.mob.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * 用户认证与授权服务
 * Created by huangy on 2015-04-14.
 */
@Service
public class SecurityMTService extends RouteDataSourceService {

    private static final Log logger = LogFactory.getLog(SecurityMTService.class);

    @Autowired
    SecurityService mSevice;


    /**
     * @param user
     * @return
     */
    public BizResponse<XTYH> login(LoginUser user) {

        BizResponse<XTYH> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.PORTAL);
        try {
            XTYH xtyh = mSevice.getUser(user.getUsername(), user.getJgid());
            if (xtyh == null) {
                result.isSuccess = false;
                result.errorMessage = "用户不存在";
                return result;
            }


            String password = user.getPassword();

            if (StringUtils.isEmpty(xtyh.YHMM) && !StringUtils.isEmpty(password)) {
                result.isSuccess = false;
                result.errorMessage = "用户密码错误";
                return result;
            }


            //传入的是转换后的密码
            if (xtyh.YHMM.equals(password)) {
                result.isSuccess = true;
                result.data = xtyh;
                return result;
            }


            String yhid = xtyh.YHID;
            //MD5加密YHID和YHMM
            String pwd = MD5Util.getMD5(yhid + password);
            //将字母转成大写
            pwd = pwd.toUpperCase();


            if (!xtyh.YHMM.equals(pwd)) {
                result.isSuccess = false;
                result.errorMessage = "用户密码错误";
                return result;
            }

            result.isSuccess = true;
            result.data = xtyh;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }


    /**
     * 获取护士所持有的静配中心列表
     *
     * @param ygdm 医护ID
     * @param jgid 机构ID
     * @return 成功静配中心列表在 {@link BizResponse#datalist}中返回
     */
    public BizResponse<QXKZ> getCenters(String ygdm, String jgid) {

        BizResponse<QXKZ> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            List<QXKZ> datalist = mSevice.getCenters(ygdm, jgid);
            result.datalist = datalist;
            result.isSuccess = true;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }

    /**
     * 获取护士所支有的病区列表
     *
     * @param ygdm 医护代码
     * @param jgid 机构ID
     * @return 成功静配中心列表在 {@link BizResponse#datalist}中返回
     */
    public BizResponse<HSQL> getOffices(String ygdm, String jgid) {

        BizResponse<HSQL> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.HRP);
        try {
            List<HSQL> datalist = mSevice.getOffices(ygdm, jgid);
            result.datalist = datalist;
            result.isSuccess = true;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }

    /**
     * 扫描登陆
     *
     * @param barcode
     * @return
     */
    public BizResponse<XTYH> slogin(String barcode) {


        BizResponse<XTYH> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            YHTM yhtm = mSevice.getYHTM(barcode);
            if (yhtm == null) {
                result.isSuccess = false;
                result.errorMessage = "用户不存在";
                return result;
            }
            keepOrRoutingDateSource(DataSource.PORTAL);
            XTYH xtyh = mSevice.getUserByYGDM(yhtm.YGDM);
            if (xtyh == null) {
                result.isSuccess = false;
                result.errorMessage = "用户不存在";
                return result;
            }
            result.isSuccess = true;
            result.data = xtyh;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }

    /**
     * 根据护士所持有的病区列表，在MOB库中查询静配中心有无启用当前病区
     *
     * @param bqs
     * @return 静配中心启用的护士病区
     */
    public BizResponse<HSQL> filterOffices(List<HSQL> bqs) {

        BizResponse<HSQL> result = new BizResponse<>();
        if (CollectionUtils.isEmpty(bqs)) {
            result.isSuccess = true;
            result.datalist = bqs;
            return result;
        }
        keepOrRoutingDateSource(DataSource.MOB);
        try {
            bqs = mSevice.filterOffices(bqs);
            result.isSuccess = true;
            result.datalist = bqs;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }

    /**
     * 根据用户编号（ID）获取用户信息
     *
     * @param ygbh 用户编号（ID）
     * @return 成功 {@link BizResponse#data}含 {@link XTYH}信息
     */
    public BizResponse<XTYH> getXTYH(String ygbh) {

        BizResponse<XTYH> result = new BizResponse<>();
        keepOrRoutingDateSource(DataSource.PORTAL);
        try {
            XTYH xtyh = mSevice.getXTYH(ygbh);
            if (xtyh == null) {
                result.isSuccess = false;
                result.errorMessage = "用户不存在";
                return result;
            }
            result.isSuccess = true;
            result.data = xtyh;
        } catch (RuntimeException | SQLException ex) {
            logger.error(ex.getMessage(), ex);
            result.isSuccess = false;
            result.errorMessage = "业务处理失败";
        }
        return result;
    }
}
