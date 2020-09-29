package com.bsoft.mob.pivas.servlet;

import com.bsoft.mob.pivas.domain.hrp.KSDM;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by 吕自聪 on 2015/10/22.
 */
public class InitialServlet extends HttpServlet{
    MemcachedClient memcachedClient;

    @Override
    public void init(ServletConfig config) throws ServletException {
      /*  WebApplicationContext wac = (WebApplicationContext)config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        memcachedClient = (MemcachedClient) wac.getBean("memcachedClient");

        //Properties properties = new Properties();
        //InputStream in = InitialServlet.class.getClassLoader().getResourceAsStream("memcached.properties");
        try {
            //properties.load(in);
            //memcachedClient = new MemcachedClient(AddrUtil.getAddresses(properties.getProperty("org.mybatis.caches.memcached.servers")));
            List<KSDM> wards = getAllOffices();
            for(KSDM ward : wards)
                memcachedClient.add("ward"+ward.KSDM,0,ward.KSMC);
            //memcachedClient.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }*//*finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    private List<KSDM> getAllOffices(){
        Properties properties = new Properties();
        InputStream in = InitialServlet.class.getClassLoader().getResourceAsStream("jdbc.properties");

        List<KSDM> wards = new ArrayList<>();
        try{
            properties.load(in);
            if(in!=null){
                in.close();
            }
            String driverName = properties.getProperty("hrp.driverClassName");
            String url = properties.getProperty("hrp.url");
            String username = properties.getProperty("hrp.username");
            String password = properties.getProperty("hrp.password");
            //加载MySql的驱动类
            Class.forName(driverName) ;
            Connection con =
                    DriverManager.getConnection(url, username, password) ;
//            PreparedStatement pstmt = con.prepareStatement("SELECT KSDM , KSMC ,JGID FROM V_MOB_HIS_KSDM");
//            ResultSet rs = pstmt.executeQuery();
            Statement stmt = con.createStatement() ;
            ResultSet rs = stmt.executeQuery("SELECT KSDM , KSMC  FROM V_MOB_HIS_KSDM");
            while(rs.next()){
                KSDM ksdm = new KSDM();
                ksdm.KSDM = rs.getString("KSDM");
                ksdm.KSMC = rs.getString("KSMC");
                wards.add(ksdm);
            }
            if(rs != null){   // 关闭记录集
                try{
                    rs.close() ;
                }catch(SQLException e){
                    e.printStackTrace() ;
                }
            }
            if(stmt != null){   // 关闭声明
                try{
                    stmt.close() ;
                }catch(SQLException e){
                    e.printStackTrace() ;
                }
            }
            if(con != null){  // 关闭连接对象
                try{
                    con.close() ;
                }catch(SQLException e){
                    e.printStackTrace() ;
                }
            }
            return wards;

        }catch(ClassNotFoundException e){
            System.out.println("找不到驱动程序类 ，加载驱动失败！");
            e.printStackTrace() ;
        } catch (SQLException e) {
            System.out.println("链接数据库失败");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("数据库配置文件加载失败");
            e.printStackTrace();
        }

        return null;
    }
}
