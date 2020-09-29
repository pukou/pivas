package com.bsoft.mob.pivas.filter;


import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * 用Filter 实现用户登陆验证
 * Created by huangy on 2015-04-28.
 */
public class AuthFilter implements Filter {
    MemcachedClient memClient;

    final static Log logger = LogFactory.getLog(AuthFilter.class.getSimpleName());


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext wac = (WebApplicationContext)filterConfig.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        memClient = (MemcachedClient)wac.getBean("memcachedClient");

       /* String memcached = filterConfig.getInitParameter("memcached");
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(memcached));
            memClient = new MemcachedClient(AddrUtil.getAddresses(properties.getProperty("org.mybatis.caches.memcached.servers")));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        AuthenticationRequestWrapper requestWrapper = new AuthenticationRequestWrapper(httpServletRequest);

        String method = httpServletRequest.getMethod();
        if ("OPTIONS".equals(method)) {
            chain.doFilter(requestWrapper, response);
            return;
        }

        if (memClient == null) {
            logger.error("MemcachedClient is null ,please check memcached.properties  is existed");
            chain.doFilter(requestWrapper, response);
            return;
        }

        String body = requestWrapper.getPayload();
        body=new String(body.getBytes(),"UTF-8");
        String token = null;
        try {
            JSONObject jsonObject = new JSONObject(body);
            token = jsonObject.getString("token");
        } catch (JSONException exception) {
            logger.error(exception.getMessage(), exception);
        }

        if (StringUtils.isEmpty(token)) {
            ((HttpServletResponse) response).setStatus(401);
            return;
        }
        Object user = memClient.get(token);
        if (user == null) {
            //未登陆返回401状态码
            ((HttpServletResponse) response).setStatus(401);
        } else {
            chain.doFilter(requestWrapper, response);
        }

    }

    @Override
    public void destroy() {
        //memClient.shutdown();
    }
}
