package com.bsoft.mob.pivas.exception;

import com.bsoft.mob.service.BizResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.script.ScriptException;
import java.io.IOException;
import java.text.ParseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {RuntimeException.class, ParseException.class, IOException.class, ScriptException.class,HttpSessionRequiredException.class})
    public
    @ResponseBody
    BizResponse<Exception> handleGlobalException(Exception ex) {

        logger.error(ex.getMessage(), ex);
        BizResponse<Exception> response = new BizResponse<>();
        response.errorMessage = "请求处理失败";
        response.data = ex;
        return response;
    }


}
