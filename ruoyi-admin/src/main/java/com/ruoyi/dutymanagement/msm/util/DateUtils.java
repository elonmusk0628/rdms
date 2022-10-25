package com.ruoyi.dutymanagement.msm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    /**
     * date类型转String(格式化)
     * @param date
     * @return
     */
    public static String dateRurnStrFormat(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);
        return dateString;
    }

    /**
     * 获取昨天日期
     * @return
     */
    public static String getYesterDay(){
        Calendar cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
        return yesterday;
    }

    /**
     * 获取明天日期
     * @return
     */
    public static String getTomorrow(){
        Calendar cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   1);
        String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
        return yesterday;
    }
}
