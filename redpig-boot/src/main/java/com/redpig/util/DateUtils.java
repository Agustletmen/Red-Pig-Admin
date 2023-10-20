package com.redpig.util;

import cn.hutool.core.date.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DateUtils {
    public static Date subMin(int min){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, -min);// 减去分钟数
        Date nowTime = cal.getTime();
        return nowTime;
    }

    public static Date addMin(int min){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, min);// 加分钟数
        Date nowTime = cal.getTime();
        return nowTime;
    }

    public static Date subDay(int day){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, -day);// 减去天数
        Date nowTime = cal.getTime();
        return nowTime;
    }

    public static Date addDay(int day){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, day);// 加天数
        Date nowTime = cal.getTime();
        return nowTime;
    }

    public static Date getCurrentZero(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 往前推day天数
     * @param day
     * @return
     */
    public static Date getCurrentZero(int day){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.DAY_OF_MONTH,-day);
        return c.getTime();
    }

    //获取指定日期的凌晨到下个凌晨时间
    public static Date[] getBeginEnd(int day){
        Date begin = getCurrentZero(day);

        Date[] dates = {getCurrentZero(day),getCurrentZero(day-1)};

        return dates;
    }

    public static void main(String[] args) {
        System.out.println(getBeginEnd(1)[0]);
        System.out.println(getBeginEnd(1)[1]);
        System.out.println(new Random().nextInt(10));
    }

    public static String format(Date date) {
        return DateUtil.format(date,"yyyy-MM-dd HH:mm:ss");
    }
}
