import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by huangyistrator on 2015/7/14.
 */

public class DateTest {

    @Test
    public void testGetTimeZone(){
        Calendar.getInstance(Locale.CHINA);

    }

    @Test
    public  void testSplit(){
        String str ="A1150701000005|-1|A1150701000005标签已停嘱,不能计费!";
        String[] array = str.split("\\|");
        Assert.assertEquals(3,array.length);
    }
}
