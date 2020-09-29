package com.bsoft.mob.pivas.controller.biz;

import com.bsoft.mob.cache.LoginUser;
import com.bsoft.mob.cache.UserStorage;
import com.bsoft.mob.datasource.DataSource;
import com.bsoft.mob.pivas.pojo.ArrayRequest;
import com.bsoft.mob.pivas.pojo.ComplexRequest;
import com.bsoft.mob.pivas.pojo.Response;
import com.bsoft.mob.pivas.pojo.biz.RefuseSignRqt;
import com.bsoft.mob.pivas.service.biz.DBMTService;
import com.bsoft.mob.pivas.service.biz.SFMTService;
import com.bsoft.mob.pivas.service.commons.DateTimeService;
import com.bsoft.mob.service.BizResponse;
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
 * 病区签收Controller
 * Created by huangy on 2015-03-27.
 */
@Controller
@RequestMapping("/auth/sign")
public class SignController {


    @Autowired
    DBMTService dbmtService;

    @Autowired
    SFMTService sfmtService;


    @Autowired
    UserStorage userStorage;

    @Autowired
    DateTimeService dateTimeService;

    /**
     * 拒绝签收
     *
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "refuse", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<Integer> refuse(@Valid @RequestBody ComplexRequest<RefuseSignRqt> request,
                             BindingResult result) {

        Response<Integer> response = new Response<>();

        if (result.hasErrors()) {
            response.isSuccess = false;
            response.errorMessage = result.getFieldError().toString();
            return response;
        }

        RefuseSignRqt refuseSignRqt = request.getData();
        //条形码
        String txm = refuseSignRqt.getCode();
        //拒绝原因
        String yyxh = refuseSignRqt.getYyxh();

        LoginUser user = userStorage.findUser(request.getToken());
        //获取当前时间
        String time = dateTimeService.now(DataSource.MOB);
        if (StringUtils.isEmpty(time)) {
            LocalDateTime localDate = LocalDateTime.now();
            time = localDate.toString("yyyy-MM-dd HH:mm:ss");
        }

        //获取员工代码
        String ygdm = user.getYgdm();

        BizResponse<Integer> bizResponse = sfmtService.refuseSign(txm, yyxh, time, ygdm);
        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }

        response.isSuccess = true;
        response.data = bizResponse.data;
        return response;
    }

    /**
     * 签收(包括简易和精准模式)
     *
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "accept", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<Integer> sign(@Valid @RequestBody ArrayRequest<Long> request,
                           BindingResult result) {

        Response<Integer> response = new Response<>();

        if (result.hasErrors()) {
            response.isSuccess = false;
            response.errorMessage = result.getFieldError().toString();
            return response;
        }


        Long[] jlxhs = request.getData();
        LoginUser user = userStorage.findUser(request.getToken());
        //获取当前时间
        String time = dateTimeService.now(DataSource.MOB);
        if (StringUtils.isEmpty(time)) {
            LocalDateTime localDate = LocalDateTime.now();
            time = localDate.toString("yyyy-MM-dd HH:mm:ss");
        }

        //获取员工代码
        String ygdm = user.getYgdm();

        return  sfmtService.sign(jlxhs, time, ygdm);
    }

}
