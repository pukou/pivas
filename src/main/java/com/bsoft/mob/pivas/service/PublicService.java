package com.bsoft.mob.pivas.service;

import com.bsoft.mob.pivas.domain.hrp.KSDM;
import com.bsoft.mob.pivas.persistence.mob.WorkFlowMapper;
import com.bsoft.mob.pivas.service.biz.SFMTService;
import com.bsoft.mob.pivas.service.commons.OfficeMTService;
import com.bsoft.mob.service.BizResponse;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * 公用方法
 *
 * Created by 吕自聪 on 2015/10/15.
 */
@Service
public class PublicService {

    @Autowired
    WorkFlowMapper workFlowMapper;

    @Autowired
    OfficeMTService officeMTService;

    @Autowired
    MemcachedClient memcachedClient;

    //检查前置模块是否执行
    public boolean WorkFolwControl(String mkbh,Long jlxh){
        int mk=Integer.parseInt(mkbh);
        String ztzd="SHHD";
        switch(mk){
            case 1://审方处理
                ztzd="SHHD";
                break;
            case 2://标签打印
                ztzd="DYCS";
                break;
            case 3://按签摆药
                ztzd="BYBZ";
                break;
            case 4://摆药核对
                ztzd="BYHD";
                break;
            case 5://发药计费
                ztzd="FYZT";
                break;
            case 6://成品核对
                ztzd="CPHD";
                break;
            case 7://打包装箱
                ztzd="DBHD";
                break;
            case 8://病区签收
                ztzd="BQQS";
                break;
            case 9://退药处理
                ztzd="TYZT";
                break;
            default:
                break;
        }
        int zt = workFlowMapper.check(ztzd,jlxh);
        return zt>=1?true:false;
    }

    public String getWardName(String bqdm){
        //Properties properties = new Properties();
        //InputStream in = PublicService.class.getClassLoader().getResourceAsStream("memcached.properties");
        try {
//            properties.load(in);
            //memcachedClient = new MemcachedClient(AddrUtil.getAddresses(properties.getProperty("org.mybatis.caches.memcached.servers")));
            String ksmc = (String) memcachedClient.get("ward" + bqdm);
            if(ksmc==null){
                BizResponse<KSDM> ksdms = officeMTService.getAllOffices();
                if(ksdms!=null){
                    for(KSDM ward: ksdms.datalist){
                        memcachedClient.add("ward"+ward.KSDM,0,ward.KSMC);
                    }
                }
                ksmc = (String) memcachedClient.get("ward" + bqdm);
            }
            //memcachedClient.shutdown();
            return ksmc;
        } catch (Exception e) {
            e.printStackTrace();
        }/*finally {
            if(in!=null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }*/
        return "未知病区";
    }
}
