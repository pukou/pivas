package com.bsoft.mob.pivas.controller.barcode;

import com.bsoft.mob.cache.LoginUser;
import com.bsoft.mob.cache.UserStorage;
import com.bsoft.mob.pivas.domain.mob.SF01;
import com.bsoft.mob.pivas.domain.mob.SF02;
import com.bsoft.mob.pivas.pojo.ComplexRequest;
import com.bsoft.mob.pivas.pojo.Response;
import com.bsoft.mob.pivas.pojo.SimpleRequest;
import com.bsoft.mob.pivas.pojo.biz.DoctorOrder;
import com.bsoft.mob.pivas.pojo.biz.GetDDRqt;
import com.bsoft.mob.pivas.pojo.biz.PkgMsg;
import com.bsoft.mob.pivas.service.biz.DBMTService;
import com.bsoft.mob.pivas.service.biz.SFMTService;
import com.bsoft.mob.pivas.service.commons.OrderMTService;
import com.bsoft.mob.service.BizResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 条码有关操作
 * Created by huangy on 2015-03-27.
 */
@Controller
public class BarcodeController {

    @Autowired
    SFMTService sfmtService;


    @Autowired
    UserStorage userStorage;


    @Autowired
    OrderMTService orderMTService;


    @Autowired
    DBMTService dbmtService;


    /**
     * 获取打包标签信息：
     * 打包人，打包批次，所属病区、总袋数、医嘱标签简洁列表
     *
     * @param request {@link SimpleRequest#data} 值为打包条码字符串
     * @param result  成功{@link Response#data}包含打包信息
     * @return
     */
    @RequestMapping(value = "/auth/barcode/product", method = RequestMethod.POST)
    @ResponseBody
    Response<PkgMsg> getPkgMsg(@Valid @RequestBody SimpleRequest request,
                               BindingResult result) {


        Response<PkgMsg> response = new Response<>();

        if (result.hasErrors()) {
            response.isSuccess = false;
            response.errorMessage = result.getFieldError().toString();
            return response;
        }

        String dbtm = request.getData();

        BizResponse<PkgMsg> bizResponse = dbmtService.getPkgMsg(dbtm);

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
     * 获取医嘱标签信息(SF01+SF02)：
     * 患者信息、药品信息、当前标签状态（已摆药、已核对、已计费、已成品、已打包、已签收、拒签）
     *
     * @param request {@link SimpleRequest#data} 值为条码字符串
     * @param result
     * @return 成功{@link Response#data}包含标签信息
     */
    @RequestMapping(value = "/auth/barcode/dd", method = RequestMethod.POST)
    public
    @ResponseBody
    Response<DoctorOrder> getDoctorOrder(@Valid @RequestBody ComplexRequest<GetDDRqt> request,
                                         BindingResult result) {

        Response<DoctorOrder> response = new Response<>();

        if (result.hasErrors()) {
            response.isSuccess = false;
            response.errorMessage = result.getFieldError().toString();
            return response;
        }

        LoginUser user = userStorage.findUser(request.getToken());

        GetDDRqt data = request.getData();


        SF01 sf01 = null;
        boolean checkStop = data.isCheckStop();
        //需要停嘱判断
        if (checkStop) {

            Response<SF01> bizResponse = sfmtService.checkSFIfStopped(data.getCode(), user.getJgid());
            if (!bizResponse.isSuccess) {
                response.isSuccess = false;
                response.errorMessage = bizResponse.errorMessage;
                response.errorflag = bizResponse.errorflag;
                return response;
            }
            //未停嘱，bizResponse.data含SF01
            sf01 = bizResponse.data;
        } else {

            //获取SF01表中的记录
            BizResponse bizResponse = sfmtService.getSF01(data.getCode(), user.getJgid());

            if (!bizResponse.isSuccess) {
                response.isSuccess = false;
                response.errorMessage = bizResponse.errorMessage;
                return response;
            }
            sf01 = (SF01) bizResponse.data;
        }


        //查询药品列表
        BizResponse bizResponse = sfmtService.getSF02s(sf01.JLXH);
        if (!bizResponse.isSuccess) {
            response.isSuccess = false;
            response.errorMessage = bizResponse.errorMessage;
            return response;
        }

        List<SF02> sf02s = bizResponse.datalist;
        DoctorOrder doctorOrder = new DoctorOrder();
        doctorOrder.sf01 = sf01;
        doctorOrder.sf02s = sf02s;
        response.data = doctorOrder;
        response.isSuccess = true;
        return response;
    }

}
