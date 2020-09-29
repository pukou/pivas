package com.bsoft.mob.pivas.controller.security;

import com.bsoft.mob.cache.LoginUser;
import com.bsoft.mob.cache.TokenStorage;
import com.bsoft.mob.cache.UserStorage;
import com.bsoft.mob.datasource.DataSource;
import com.bsoft.mob.pivas.domain.hrp.HSQL;
import com.bsoft.mob.pivas.domain.mob.QXKZ;
import com.bsoft.mob.pivas.domain.portal.XTYH;
import com.bsoft.mob.pivas.pojo.Response;
import com.bsoft.mob.pivas.pojo.security.LoginResponse;
import com.bsoft.mob.pivas.service.commons.DateTimeService;
import com.bsoft.mob.pivas.service.security.SecurityMTService;
import com.bsoft.mob.pivas.service.system.SystemMTService;
import com.bsoft.mob.service.BizResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 负责用户认证Controller
 * Created by huangy on 2015-03-17.
 */
@Controller
public class SecurityController {

    @Autowired
    UserStorage userStorage;

    @Autowired
    TokenStorage tokenStorage;

    @Autowired
    SecurityMTService securityMTService;

    @Autowired
    SystemMTService systemMTService;

    @Autowired
    DateTimeService dateTimeService;

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();


    /**
     * 用户登陆
     *
     * @param user     登陆用户
     * @param result   用户验证结果
     * @param location 0表示静配中心使用，非0表示病区使用
     * @return 成功返回
     */
    @RequestMapping(value = "/login/{location}", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<LoginResponse> login(@Valid @RequestBody LoginUser user,
                                  BindingResult result, @PathVariable String location) {

        Response<LoginResponse> response = new Response<>();

        if (result.hasErrors()) {
            response.isSuccess = false;
            response.errorMessage = result.getFieldError().toString();
            return response;
        }

        // 查询数据库，进行用户验证
        BizResponse bizResponse = securityMTService.login(user);
        //login error
        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }

        XTYH xtyh = (XTYH) bizResponse.data;

        return getLoginedInfo(xtyh, user, location);
    }

    /**
     * 扫描登陆
     *
     * @param barcode 员工胸卡条码
     * @return
     */
    @RequestMapping(value = "/slogin/{location}", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<LoginResponse> scanLogin(@RequestParam String barcode, @PathVariable String location, @RequestParam("device") String deviceId) {

        Response<LoginResponse> response = new Response<>();

        BizResponse bizResponse = securityMTService.slogin(barcode);
        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }
        XTYH xtyh = (XTYH) bizResponse.data;

        LoginUser user = new LoginUser();
        user.setUsername(xtyh.YHDM);
        user.setPassword(xtyh.YHMM);
        user.setJgid(xtyh.JGID);
        user.setDeviceId(deviceId);

        return getLoginedInfo(xtyh, user, location);

    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<?> logout(@RequestParam String token) {

        Response<?> response = new Response<>();

        LoginUser userToken = userStorage.findUser(token);
        if (userToken != null) {
            userStorage.remove(token);
            tokenStorage.remove(userToken.getYgdm());
            response.isSuccess = true;
            return response;
        }
        response.errorMessage = "退出登陆失败";
        return response;
    }


    private Response<LoginResponse> getLoginedInfo(XTYH xtyh, LoginUser user, String location) {


        LoginResponse data = new LoginResponse();
        data.xtyh = xtyh;

        BizResponse bizResponse = null;
        Response<LoginResponse> response = new Response<>();

        String ygdm = xtyh.YHID;
        String jgid = user.getJgid();

        //用于保存静配中心ID
        List<String> jpids;

        //在静配中心使用
        if ("0".equals(location)) {
            //获取护士静配中心房药权限
            bizResponse = securityMTService.getCenters(ygdm, jgid);
            if (!bizResponse.isSuccess) {
                response.isSuccess = false;
                response.errorMessage = bizResponse.errorMessage;
                return response;
            }
            data.jpzxs = bizResponse.datalist;

            //获取静配中心ID列表
            jpids = new ArrayList<>(data.jpzxs.size());
            for (QXKZ qxkz : data.jpzxs) {
                jpids.add(qxkz.JPBH);
            }

        } else {//其他默认是在病区使用
            //获取病区列表
            bizResponse = securityMTService.getOffices(ygdm, jgid);
            if (!bizResponse.isSuccess) {
                response.isSuccess = false;
                response.errorMessage = bizResponse.errorMessage;
                return response;
            }
            List<HSQL> bqs = bizResponse.datalist;

            //过滤病区列表，并关联病区静配中心ID
            bizResponse = securityMTService.filterOffices(bqs);
            if (!bizResponse.isSuccess) {
                response.isSuccess = false;
                response.errorMessage = bizResponse.errorMessage;
                return response;
            }
            data.bqs = bizResponse.datalist;

            //获取静配中心ID列表
            jpids = new ArrayList<>(data.bqs.size());
            for (HSQL hsql : data.bqs) {
                jpids.add(String.valueOf(hsql.JPBH));
            }
        }

        String[] jpArray = jpids.toArray(new String[jpids.size()]);
        //获取每个静配中心启动的参数，例如摆药核对自动确认
        bizResponse = systemMTService.getUseConfigs(jgid, jpArray);
        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }
        data.ucs = bizResponse.datalist;


        //获取每个静配中心PDA的工作流程数据
        bizResponse = systemMTService.getMKIDByYGDM(jgid,ygdm);
        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }
        data.jplcs = bizResponse.datalist;


        //获取每个静配中心的条码前缀
        bizResponse = systemMTService.getBarCodePrefixs(jgid, jpArray);
        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }
        data.prefixs = bizResponse.datalist;

        //保存用户并获取请求令牌
        user.setYgdm(ygdm);
        String tokenStr = UUID.randomUUID().toString();
        boolean added = userStorage.addUser(tokenStr, user);
        if (!added) {
            response.isSuccess = false;
            response.errorMessage = "服务端缓存Token失败";
            return response;
        }

        replaceToken(tokenStr, ygdm);

        //获取当前时间
        data.now = dateTimeService.now(DataSource.MOB);

        data.token = tokenStr;
        response.data = data;
        response.isSuccess = true;
        return response;
    }


    /**
     * 移除旧token，并缓存新token
     *
     * @param token
     * @param ygdm
     */
    public void replaceToken(final String token, final String ygdm) {

        cachedThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                String oldToken = tokenStorage.findToken(ygdm);
                if (!StringUtils.isEmpty(oldToken)) {
                    userStorage.remove(oldToken);
                    tokenStorage.remove(ygdm);
                }
                tokenStorage.addToken(ygdm, token);
            }
        });
    }
}
