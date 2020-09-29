package com.bsoft.mob.pivas.controller.update;

import com.bsoft.mob.pivas.pojo.Response;
import com.bsoft.mob.pivas.pojo.update.UpdateInfoVo;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * APP更新管理
 * Created by huangy on 2015/2/26 0026.
 */
@Controller
public class UpdateController {


    /**
     * 检测版本信息
     *
     * @param version 应用现版本
     * @param appname 应用名称
     * @param request
     * @return 如有新的应用版本，则返回 Response.UpdateInfoVo
     * @throws java.io.IOException
     */
    @RequestMapping(value = "/checkversion",method = RequestMethod.GET)
    public
    @ResponseBody
    Response<UpdateInfoVo> processCheck(
            @RequestParam(value = "version") int version, @RequestParam(value = "name") String appname,
            HttpServletRequest request) throws IOException {

        Response<UpdateInfoVo> bizResponse = new Response<>();
        String desFilePath = request.getServletContext().getRealPath("") + File.separator + "apps" + File.separator + appname.toLowerCase() + File.separator + "des.json";
        byte[] encoded = Files.readAllBytes(Paths.get(desFilePath));
        String jsonStr = new String(encoded, "utf-8");
        JSONObject des = new JSONObject(jsonStr);
        if (des.getInt("version") > version) {//有新的应用
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
            String url = basePath + "apps/" + appname + "/" + des.getString("file");
            UpdateInfoVo data = new UpdateInfoVo();
            data.des = des.getString("des");
            data.fileName = des.getString("file");
            data.url = url;
            data.versionName = des.getString("versionName");
            bizResponse.data = data;
            bizResponse.isSuccess = true;
        } else {
            bizResponse.errorMessage = "当前已为最新版本，无需更新";
        }
        return bizResponse;
    }
}
