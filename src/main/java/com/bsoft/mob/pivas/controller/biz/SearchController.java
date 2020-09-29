package com.bsoft.mob.pivas.controller.biz;

import com.bsoft.mob.cache.LoginUser;
import com.bsoft.mob.cache.UserStorage;
import com.bsoft.mob.domain.mob.SystemConfig;
import com.bsoft.mob.pivas.domain.mob.SF01;
import com.bsoft.mob.pivas.domain.mob.SF02;
import com.bsoft.mob.pivas.domain.mob.THYY;
import com.bsoft.mob.pivas.pojo.ComplexRequest;
import com.bsoft.mob.pivas.pojo.Response;
import com.bsoft.mob.pivas.pojo.SimpleRequest;
import com.bsoft.mob.pivas.pojo.biz.*;
import com.bsoft.mob.pivas.service.biz.SFMTService;
import com.bsoft.mob.pivas.service.biz.StatisMTService;
import com.bsoft.mob.pivas.service.system.SystemMTService;
import com.bsoft.mob.service.BizResponse;
import com.bsoft.mob.service.mob.dic.ConfigMaintainService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 相关查询Controller，包括统计、汇总查询、SF01 、SFO2查询
 * Created by huangy on 2015-03-27.
 */
@Controller
@RequestMapping("/auth")
public class SearchController {


    @Autowired
    SFMTService sfmtService;


    @Autowired
    UserStorage userStorage;

    @Autowired
    SystemMTService systemMTService;

    @Autowired
    ConfigMaintainService configMaintainService;

    @Autowired
    StatisMTService statisMTService;


    /**
     * 标签汇总查询
     *
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "statis/all", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<StatisAll> statisAll(@Valid @RequestBody ComplexRequest<StatisAllRqt> request,
                                  BindingResult result) {

        Response<StatisAll> response = new Response<>();

        if (result.hasErrors()) {
            response.isSuccess = false;
            response.errorMessage = result.getFieldError().toString();
            return response;
        }

        StatisAllRqt statisAllRqt = request.getData();

        BizResponse<StatisAll> bizResponse = statisMTService.statisAll(statisAllRqt.getJpbh(), statisAllRqt.getBqValue(), statisAllRqt.getFsValue(), statisAllRqt.getPcValue(), statisAllRqt.getYpValue(), statisAllRqt.getBegin(), statisAllRqt.getEnd());

        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }

        response.isSuccess = true;
        response.datalist = bizResponse.datalist;

        return response;
    }


    /**
     * 个人工作量查询
     *
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "statis/my", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<StatisMy> statisMy(@Valid @RequestBody ComplexRequest<StatisMyRqt> request,
                                BindingResult result) {

        Response<StatisMy> response = new Response<>();

        if (result.hasErrors()) {
            response.isSuccess = false;
            response.errorMessage = result.getFieldError().toString();
            return response;
        }
        LoginUser user = userStorage.findUser(request.getToken());
        String ygdm = user.getYgdm();

        StatisMyRqt statisMyRqt = request.getData();

        BizResponse<StatisMy> bizResponse = statisMTService.statisMy(ygdm, statisMyRqt.getJpbh(), statisMyRqt.getBegin(), statisMyRqt.getEnd());

        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }

        response.isSuccess = true;
        response.datalist = bizResponse.datalist;

        return response;
    }

    /**
     * 标签跟踪查询
     *
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "trank/barcode", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<TrankMsg> trank(@Valid @RequestBody SimpleRequest request,
                             BindingResult result) {

        Response<TrankMsg> response = new Response<>();

        if (result.hasErrors()) {
            response.isSuccess = false;
            response.errorMessage = result.getFieldError().toString();
            return response;
        }


        LoginUser user = userStorage.findUser(request.getToken());
        String jgid = user.getJgid();

        BizResponse<TrankMsg> bizResponse = sfmtService.trank(request.getData(), jgid);

        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }

        response.isSuccess = true;
        response.datalist = bizResponse.datalist;

        return response;
    }

    /**
     * 获取标签列表数据
     *
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "sf01s", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<SF01> findSF01s(@Valid @RequestBody ComplexRequest<GetSF01sRqt> request,
                             BindingResult result) {

        Response<SF01> response = new Response<>();

        if (result.hasErrors()) {
            response.isSuccess = false;
            response.errorMessage = result.getFieldError().toString();
            return response;
        }

        GetSF01sRqt data = request.getData();


        int sj = data.getSj();

        LocalDate time = null;
        if (sj == 1) {
            time = LocalDate.now();
        } else if (sj == 2) {
            time = LocalDate.now().plusDays(1);
        }


        //设置查询开始与结束时间时间段
        String begin = time.toString("yyyy-MM-dd ") + "00:00";
        String end = time.toString("yyyy-MM-dd ") + "23:59";


        LoginUser user = userStorage.findUser(request.getToken());


        BizResponse<SF01> bizResponse = sfmtService.getSF01s(data.getBq(), data.getPc(), data.getPzfs(), data.getZylx(), data.getFzcx(), begin, end, user.getJgid(), data.getJpbh(), data.isAll());

        //TODO test
        //BizResponse<SF01> bizResponse = sfmtService.getSF01s(null, null, null, null, null, null, null, user.getJgid(), data.getJpbh(), true);


        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }

        response.isSuccess = bizResponse.isSuccess;
        response.datalist = bizResponse.datalist;
        return response;
    }


    /**
     * 获取SFO2数据
     *
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "sf02s", method = RequestMethod.POST)
    @ResponseBody
    Response<SF02> findSF02s(@Valid @RequestBody SimpleRequest request,
                             BindingResult result) {


        Response<SF02> response = new Response<>();

        if (result.hasErrors()) {
            response.isSuccess = false;
            response.errorMessage = result.getFieldError().toString();
            return response;
        }

        String jlxh = request.getData();

        //查询药品列表
        BizResponse bizResponse = sfmtService.getSF02s(Long.valueOf(jlxh));
        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }

        List<SF02> sf02s = bizResponse.datalist;
        response.datalist = sf02s;
        response.isSuccess = true;
        return response;
    }

    /**
     * 获取标签列表查询因子
     *
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "sf01/factor", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<Factor> getFactor(@Valid @RequestBody SimpleRequest request,
                               BindingResult result) {

        Response<Factor> response = new Response<>();

        if (result.hasErrors()) {
            response.isSuccess = false;
            response.errorMessage = result.getFieldError().toString();
            return response;
        }
        //静配中心ID
        String jpbh = request.getData();

        //获取静配病区列表
        BizResponse<Map<String, String>> bizResponse = systemMTService.getOffices(jpbh);
        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }
        Factor factor = new Factor();
        factor.bqs = new TreeMap<>(bizResponse.data);
        factor.bqs.put("-1", "所有病区");

        //获取配制批次列表
        bizResponse = systemMTService.getPCs(jpbh);
        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }
        factor.pcs = new TreeMap<>(bizResponse.data);
        factor.pcs.put("-1", "所有批次");

        //获取主药类型列表
        BizResponse<SystemConfig> configBizResponse = configMaintainService.getConfigsByDmlb("602");
        if (!configBizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = configBizResponse.errorMessage;
            return response;
        }
        TreeMap<String, String> zylx = new TreeMap<>();
        for (SystemConfig systemConfig : configBizResponse.datalist) {
            zylx.put(systemConfig.DMSB, systemConfig.DMMC);
        }

        factor.zylx = zylx;
        factor.zylx.put("-1", "所有类型");

        //获取配制方式
        TreeMap<String, String> pzfs = new TreeMap<>();
        pzfs.put("0", "打包");
        pzfs.put("1", "配置");
        factor.pzfs = pzfs;
        factor.pzfs.put("-1", "所有方式");

        response.isSuccess = true;
        response.data = factor;
        return response;
    }

    /**
     * 获取退回原因列表
     *
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "refuse/items", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<THYY> getTHYYs(@Valid @RequestBody SimpleRequest request,
                            BindingResult result) {

        Response<THYY> response = new Response<>();

        if (result.hasErrors()) {
            response.isSuccess = false;
            response.errorMessage = result.getFieldError().toString();
            return response;
        }
        //退回类型
        String thlx = request.getData();

        LoginUser user = userStorage.findUser(request.getToken());
        String jgid = user.getJgid();

        //获取数据
        BizResponse<THYY> bizResponse = systemMTService.getTHYYs(thlx, jgid);
        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }

        response.isSuccess = true;
        response.datalist = bizResponse.datalist;
        return response;
    }
}
