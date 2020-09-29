package com.bsoft.mob.pivas.controller.biz;

import com.bsoft.mob.cache.LoginUser;
import com.bsoft.mob.cache.UserStorage;
import com.bsoft.mob.datasource.DataSource;
import com.bsoft.mob.domain.mob.SystemConfig;
import com.bsoft.mob.pivas.service.PublicService;
import com.bsoft.mob.pivas.domain.mob.JPLC;
import com.bsoft.mob.pivas.domain.mob.SF01;
import com.bsoft.mob.pivas.domain.portal.XTYH;
import com.bsoft.mob.pivas.pojo.Response;
import com.bsoft.mob.pivas.pojo.SimpleRequest;
import com.bsoft.mob.pivas.pojo.biz.ActionType;
import com.bsoft.mob.pivas.service.biz.SFMTService;
import com.bsoft.mob.pivas.service.commons.DateTimeService;
import com.bsoft.mob.pivas.service.security.SecurityMTService;
import com.bsoft.mob.pivas.service.system.SystemMTService;
import com.bsoft.mob.service.BizResponse;
import com.bsoft.mob.service.mob.dic.ConfigMaintainService;
import com.bsoft.mob.util.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 摆药核对Controller
 * Created by huangy on 2015-03-27.
 */
@Controller
@RequestMapping("/auth")
public class PlaceCheckController {


    @Autowired
    SFMTService sfmtService;

    @Autowired
    UserStorage userStorage;

    @Autowired
    SecurityMTService securityMTService;

    @Autowired
    SystemMTService systemMTService;

    @Autowired
    ConfigMaintainService configMaintainService;

    @Autowired
    DateTimeService dateTimeService;

    @Autowired
    PublicService publicService;
    /**
     * 执行摆药核对
     *
     * @param request {@link SimpleRequest#data} 值为条码字符串
     * @param result
     * @return 成功{@link Response#data} 值为数据库更新的条目数据
     */
    @RequestMapping(value = "pc/check", method = RequestMethod.POST)

    public
    @ResponseBody
    Response<Integer> check(@Valid @RequestBody SimpleRequest request,
                            BindingResult result) {


        Response<Integer> response = new Response<>();

        if (result.hasErrors()) {
            response.isSuccess = false;
            response.errorMessage = result.getFieldError().toString();
            return response;
        }

        LoginUser user = userStorage.findUser(request.getToken());
        String jgid = user.getJgid();

        //1. check 医嘱是否停嘱
        Response<SF01> bizResponse = sfmtService.checkSFIfStopped(request.getData(), jgid);

        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            response.errorflag = bizResponse.errorflag;
            return response;
        }

        SF01 sf01 = bizResponse.data;
        Long jlxh = sf01.JLXH;
        //2. check已摆药核对
        if (sf01.BYHD == 1) {

            response.isSuccess = false;

            //查询员工信息
            BizResponse<XTYH> xtyhBizResponse = securityMTService.getXTYH(sf01.BYGH);
            if (!xtyhBizResponse.isSuccess) {
                response.errorMessage = xtyhBizResponse.errorMessage;
                return response;
            }

            LocalDateTime llt = LocalDateTime.parse(sf01.BYSJ, DateTimeUtil.timeFormatter);
            XTYH xtyh = xtyhBizResponse.data;
            response.errorMessage = xtyh.YHXM + "于" + llt.toString("MM-dd HH:mm") + "已核对";
            response.errorflag = 3;
            return response;
        }


        //3. 检测流程有无启用
        BizResponse<JPLC> jplcBizResponse = systemMTService.getWorkFlow(sf01.JPBH, 4);
        if (!jplcBizResponse.isSuccess) {
            response.errorMessage = jplcBizResponse.errorMessage;
            return response;
        }
        JPLC jplc = jplcBizResponse.data;

        //4. 检测前置模块有无执行
        if (jplc.QZMK != null) {
            // 如果前置模块未执行
            if(!publicService.WorkFolwControl(String.valueOf(jplc.QZMK),jlxh)){
                response.isSuccess = false;
                BizResponse<SystemConfig> systemConfigBizResponse = configMaintainService.getConfig("606", String.valueOf(jplc.QZMK));
                if (!systemConfigBizResponse.isSuccess) {
                    response.errorMessage = systemConfigBizResponse.errorMessage;
                    return response;
                }
                SystemConfig systemConfig = systemConfigBizResponse.data;
                response.errorMessage = systemConfig.DMMC + "未执行";
                return response;
            }

        }

        //获取当前的摆药时间
        String time = dateTimeService.now(DataSource.MOB);
        if (StringUtils.isEmpty(time)) {
            LocalDateTime localDate = LocalDateTime.now();
            time = localDate.toString("yyyy-MM-dd HH:mm:ss");
        }

        //获取员工代码
        String ygdm = user.getYgdm();

        //5. 执行摆药核对操作
        BizResponse<Integer> bizResponse1 = sfmtService.action(jlxh, time, ygdm, ActionType.PLACE_DRUG_CHECK,sf01.ZTBZ);
        if (!bizResponse1.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse1.errorMessage;
            return response;
        }

        response.isSuccess = true;
        response.data = bizResponse1.data;
        return response;
    }

}
