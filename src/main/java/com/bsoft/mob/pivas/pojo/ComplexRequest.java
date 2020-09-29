package com.bsoft.mob.pivas.pojo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

/**
 * 业务请求封装类。所有要验证用户信息的请求均使用此类进行传参
 * Created by huangy on 2015-04-02.
 */
public class ComplexRequest<T> {

    @NotEmpty
    private String token;

    /**
     * 请求业务对象类
     */
    @Valid
    private T data;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
