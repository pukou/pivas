package com.bsoft.mob.pivas.persistence.mob;

import com.bsoft.mob.datasource.DataSource;
import com.bsoft.mob.datasource.DataSourceContextHolder;
import com.bsoft.mob.pivas.domain.mob.SF01;
import com.bsoft.mob.pivas.domain.mob.YHTM;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by huangy on 2015-04-16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:database-mybatis-isolate.xml", "classpath:applicationContext-druid.xml"})
// 加载基本的数据源、事务管理等公用配置
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class SF01MapperTest {

    @Autowired
    SF01Mapper mapper;

    @Test
    public void testgetYHTM() {
       int count = mapper.getCount(null,"3","1","2015-06-16 00:00:00","2015-06-24 23:59:00");
        Assert.assertEquals(100,count);
    }

    @Test
    public void testgetSF01s() {
        List<SF01> result = mapper.getSF01s("-1","-1","-1","-1","BYBZ","2015-06-16 00:00:00","2015-06-24 23:59:00","1","1");
        Assert.assertNotNull(result);
    }

    @BeforeTransaction
    public void beforeTrans() {
        DataSourceContextHolder.setDataSource(DataSource.MOB);
    }

}
