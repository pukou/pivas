package com.bsoft.mob.pivas.controller.commons;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 响应客户端验证IP连接等访问
 * Created by huangy on 2015-04-23.
 */
@Controller
public class ValidController {

    @RequestMapping(value = "/valid")
    public
    @ResponseBody
    String valid() {
        return "it's ok";
    }
}
