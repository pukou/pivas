package com.bsoft.mob.pivas.persistence.hrp;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by huangy on 2015-04-15.
 */
public class NormalTest {


    public static final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Test
    public void testStr(){
        assertEquals("",null);
    }

    @Test
    public void testFormat(){
        String time ="2015-04-27 09:00:00.0";
        LocalDateTime localDateTime = LocalDateTime.parse(time, timeFormatter);
        String lld =localDateTime.toString("HH:mm");
        assertEquals("09:00",lld);

    }
}
