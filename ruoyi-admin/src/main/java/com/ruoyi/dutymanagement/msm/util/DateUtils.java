package com.ruoyi.dutymanagement.msm.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * String类型转date
     * @param str
     * @return
     * @throws ParseException
     */
    public static Date stringTurnDate(String str) throws ParseException {
        if(str==null ||"".equals(str)){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = sdf.parse(str);
        return parse;
    }

    /**
     * date类型转String
     * @param date
     * @return
     */
    public static String dateRurnString(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(date);
        return dateString;
    }
}
