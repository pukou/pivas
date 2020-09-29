package com.bsoft.mob.pivas.pojo;

import java.util.List;


/**
 * 所有WEB 服务接口正文响应类
 *
 * @param <T> 响应业务对象类，例如，LoginResult
 */
public class Response<T> {

    /**
     * 返回的错误信息
     */
    public String errorMessage;

    /**
     * 是否成功
     */
    public boolean isSuccess;

    /**
     * 错误标识 0 普通错误；其他错误类型可随接口自定义（主要是业务错误）：
     * <p/>
     * 1 已退回；
     * 2 已停嘱；
     * 3 已摆药、已核对、已成品；
     * 4 计费有关
     * 5 签收有关
     */
    public int errorflag;

    /**
     * 响应业务对象类
     */
    public T data;

    /**
     * 响应业务对象列表
     */
    public List<T> datalist;

}
