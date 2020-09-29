package com.bsoft.mob.pivas.controller.commons;

import com.bsoft.mob.domain.mob.Dictionary;
import com.bsoft.mob.service.BizResponse;
import com.bsoft.mob.service.mob.dic.DicMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 字典Controller
 * Created by huangy on 2015/2/6 0006.
 */
@Controller
public class DicController {

    @Autowired
    DicMaintainService dicService;

    /**
     * 根据代码类别，获取字典列表
     *
     * @param dmlb
     * @return
     */
    @RequestMapping(value = "/dic/{dmlb}")
    public
    @ResponseBody
    BizResponse<Dictionary> getDics(@PathVariable String dmlb) {
        return dicService.getDicsByDmlb(dmlb);

    }

    /**
     * 根据代码类别与代码识别号获取字典项
     *
     * @param dmlb
     * @param dmsb
     * @return
     */
    @RequestMapping(value = "/dic/({dmlb},{dmsb})")
    public
    @ResponseBody
    BizResponse<Dictionary> getDic(@PathVariable String dmlb,
                                @PathVariable String dmsb) {
        return  dicService.getDic(dmlb, dmsb);
    }

}
