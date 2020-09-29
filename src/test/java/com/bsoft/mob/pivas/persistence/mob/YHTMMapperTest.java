package com.bsoft.mob.pivas.persistence.mob;

import com.bsoft.mob.datasource.DataSource;
import com.bsoft.mob.datasource.DataSourceContextHolder;
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

/**
 * Created by huangy on 2015-04-16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:database-mybatis-isolate.xml", "classpath:applicationContext-druid.xml"})
// 加载基本的数据源、事务管理等公用配置
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class YHTMMapperTest {

    @Autowired
    YHTMMapper mapper;

    @Test
    public void testgetYHTM() {
        YHTM yhtm = mapper.getYHTM("XK80_1");
        Assert.assertNotNull(yhtm);
    }

    @BeforeTransaction
    public void beforeTrans() {
        DataSourceContextHolder.setDataSource(DataSource.MOB);
    }

}
