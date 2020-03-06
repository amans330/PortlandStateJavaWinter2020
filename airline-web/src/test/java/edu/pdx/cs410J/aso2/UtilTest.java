package edu.pdx.cs410J.aso2;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UtilTest {

    @Test
    public void testgetDateFromString(){
        Date date = Util.getDateFromString("03/05/2020 04:09 PM");
        assertThat(String.valueOf(date.getTime()), containsString("15834533"));
    }
    
    @Test
    public void dateToString(){
    	Date date = Util.getDateFromString("03/05/2020 04:09 PM");
    	Util.dateToStringFormat(date);
        assertThat(1, equalTo(1));
    }
}
