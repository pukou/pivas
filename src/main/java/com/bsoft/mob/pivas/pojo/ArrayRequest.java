package com.bsoft.mob.pivas.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 业务请求封装类。所有要验证用户信息的请求均使用此类进行传参
 * Created by huangy on 2015-04-02.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArrayRequest<T> {

    @NotEmpty
    private String token;

    @NotEmpty
    private T[] data;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T[] getData() {
        return data;
    }

    public void setData(T[] data) {
        this.data = data;
    }
}
