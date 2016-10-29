package com.klobliu.wow.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * *******************************************************************************
 * *********    Author : klob(kloblic@gmail.com) .
 * *********    Date : 2014-11-29  .
 * *********    Time : 11:46 .
 * *********    Project name : Diandi1.18 .
 * *********    Version : 1.0
 * *********    Copyright @ 2014, klob, All Rights Reserved
 * *******************************************************************************
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {
    public final static String FORMAT_YEAR = "yyyy";
    public final static String FORMAT_MONTH_DAY = "MM月dd日";
    public final static String FORMAT_DATE = "yyyy-MM-dd";
    public final static String FORMAT_TIME = "HH:mm";
    public final static String FORMAT_MONTH_DAY_TIME = "MM月dd日 hh:mm";
    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
    public final static String FORMAT_DATE1_TIME = "yyyy/MM/dd HH:mm";
    public final static String FORMAT_DATE_TIME_SECOND = "yyyy/MM/dd HH:mm:ss";
    public final static String BMOB_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final int YEAR = 365 * 24 * 60 * 60;// 年
    private static final int MONTH = 30 * 24 * 60 * 60;// 月
    private static final int DAY = 24 * 60 * 60;// 天
    private static final int HOUR = 60 * 60;// 小时
    private static final int MINUTE = 60;// 分钟
    private final static String start_day = getDatetimeString();
    public static PrettyTime prettyTime = new PrettyTime();
    private static SimpleDateFormat sdf = new SimpleDateFormat();
    public static String DEFAULT_FORMAT = FORMAT_DATE_TIME;

    /**
     * 根据时间戳获取描述性时间，如3分钟前，1天前
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 时间字符串
     */
    public static String getDescriptionTimeFromTimestamp(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
        System.out.println("timeGap: " + timeGap);
        String timeStr = null;
        if (timeGap > YEAR) {
            timeStr = timeGap / YEAR + "年前";
        } else if (timeGap > MONTH) {
            timeStr = timeGap / MONTH + "个月前";
        } else if (timeGap > DAY) {// 1天以上
            timeStr = timeGap / DAY + "天前";
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = timeGap / HOUR + "小时前";
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    /**
     * 获取当前日期的指定格式的字符串
     *
     * @param format 指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static String getCurrentTime(String format) {
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE_TIME);
        } else {
            sdf.applyPattern(format);
        }
        return sdf.format(new Date());
    }

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // date类型转换为String类型
    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data) {
        return dateToString(data, DEFAULT_FORMAT);
    }


    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType) {
        String strTime = "";
        Date date = longToDate(currentTime, formatType);// long类型转成Date类型
        strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }


    // long类型转换为String类型
    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime) {
        return longToString(currentTime, DEFAULT_FORMAT);

    }



    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(BMOB_FORMAT);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getThen(String strTime) {
        return prettyTime.format(stringToDate(strTime, BMOB_FORMAT));
    }

    public static String getThen(long strTime) {
        return prettyTime.format(longToDate(strTime, BMOB_FORMAT));
    }

    // long转换为Date类型
    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType) {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }



    // string类型转换为long类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
        return format.format(new Date(time));
    }

    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 获取聊天时间：因为sdk的时间默认到秒故应该乘1000
     *
     * @param @param  timesamp
     * @param @return
     * @return String
     * @throws
     * @Title: getChatTime
     * @Description: TODO
     */
    public static String getChatTime(long timesamp) {
        long clearTime = timesamp;
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(clearTime);
        int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));
        switch (temp) {
            case 0:
                result = "今天 " + getHourAndMin(clearTime);
                break;
            case 1:
                result = "昨天 " + getHourAndMin(clearTime);
                break;
            case 2:
                result = "前天 " + getHourAndMin(clearTime);
                break;

            default:
                result = getTime(clearTime);
                break;
        }

        return result;
    }

    public static String getDatetimeString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static String getDatetimeString() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date) + " " + getWeek();
    }

    public static String getDate() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        if (c.get(Calendar.MONTH) + 1 < 10)
            mMonth = "0" + mMonth;
        if (c.get(Calendar.DAY_OF_MONTH) < 10)
            mDay = "0" + mDay;
        System.out.printf("");
        String str = mYear + "-" + mMonth + "-" + mDay + " " + "星期" + mWay;
        System.out.printf(str);
        return str;

    }

    public static String getWeek() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String myWeek = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(myWeek)) {
            myWeek = "天";
        } else if ("2".equals(myWeek)) {
            myWeek = "一";
        } else if ("3".equals(myWeek)) {
            myWeek = "二";
        } else if ("4".equals(myWeek)) {
            myWeek = "三";
        } else if ("5".equals(myWeek)) {
            myWeek = "四";
        } else if ("6".equals(myWeek)) {
            myWeek = "五";
        } else if ("7".equals(myWeek)) {
            myWeek = "六";
        }
        return "星期" + myWeek;

    }

    public static String getWeek(int year, int monthOfYear, int dayOfMonth) {
        int y = year - 2000;
        int m = monthOfYear + 1;
        int c = 20;
        int d = dayOfMonth;
        int w = y + y / 4 + c / 4 - 2 * c + 26 * (m + 1) / 10 + d - 1;
        String myWeek = null;

        switch (w % 7) {
            case 0:
                myWeek = "天";
                break;
            case 1:
                myWeek = "一";
                break;
            case 2:
                myWeek = "二";
                break;
            case 3:
                myWeek = "三";
                break;
            case 4:
                myWeek = "四";
                break;
            case 5:
                myWeek = "五";
                break;
            case 6:
                myWeek = "六";
                break;
            default:
                break;
        }

        return "星期" + myWeek;
    }

    public static String getMonthOfYear(String month) {

        int m = Integer.valueOf(month) + 1;
        if (m < 13 && m > 0)
            return String.valueOf(m);
        else return null;
    }

    public static int calInterval(Date sDate, Date eDate, String type) {
        //时间间隔，初始为0
        int interval = 0;

        /*比较两个日期的大小，如果开始日期更大，则交换两个日期*/
        //标志两个日期是否交换过
        boolean reversed = false;
        if (compareDate(sDate, eDate) > 0) {
            Date dTest = sDate;
            sDate = eDate;
            eDate = dTest;
            //修改交换标志
            reversed = true;
        }

        /*将两个日期赋给日历实例，并获取年、月、日相关字段值*/
        Calendar sCalendar = Calendar.getInstance();
        sCalendar.setTime(sDate);
        int sYears = sCalendar.get(Calendar.YEAR);
        int sMonths = sCalendar.get(Calendar.MONTH);
        int sDays = sCalendar.get(Calendar.DAY_OF_YEAR);

        Calendar eCalendar = Calendar.getInstance();
        eCalendar.setTime(eDate);
        int eYears = eCalendar.get(Calendar.YEAR);
        int eMonths = eCalendar.get(Calendar.MONTH);
        int eDays = eCalendar.get(Calendar.DAY_OF_YEAR);

        //年
        if (cTrim(type).equals("Y") || cTrim(type).equals("y")) {
            interval = eYears - sYears;
            if (eMonths < sMonths) {
                --interval;
            }
        }
        //月
        else if (cTrim(type).equals("M") || cTrim(type).equals("m")) {
            interval = 12 * (eYears - sYears);
            interval += (eMonths - sMonths);
        }
        //日
        else if (cTrim(type).equals("D") || cTrim(type).equals("d")) {
            interval = 365 * (eYears - sYears);
            interval += (eDays - sDays);
            //除去闰年天数
            while (sYears < eYears) {
                if (isLeapYear(sYears)) {
                    --interval;
                }
                ++sYears;
            }
        }
        //如果开始日期更大，则返回负值
        if (reversed) {
            interval = -interval;
        }
        //返回计算结果
        return interval;
    }

    private static int compareDate(Date sDate, Date eDate) {
        int result = 0;
        //将开始时间赋给日历实例
        Calendar sC = Calendar.getInstance();
        sC.setTime(sDate);
        //将结束时间赋给日历实例
        Calendar eC = Calendar.getInstance();
        eC.setTime(eDate);
        //比较
        result = sC.compareTo(eC);
        //返回结果
        return result;
    }

    public static String cTrim(String tStr) {
        String ttStr = "";
        if (tStr == null) {
        } else {
            ttStr = tStr.trim();
        }
        return ttStr;
    }

    private static boolean isLeapYear(int year) {
        return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0));
    }

    public static boolean otherDay(String end_day) {
        if (TextUtils.isEmpty(end_day)) {
            return false;
        }
        int startYear, startMonth, startDay, endYear, endMonth, endDay;
        startYear = Integer.valueOf(start_day.substring(0, 4));
        startMonth = Integer.valueOf(start_day.substring(5, 7));
        startDay = Integer.valueOf(start_day.substring(8, 10));

        endYear = Integer.valueOf(end_day.substring(0, 4));
        endMonth = Integer.valueOf(end_day.substring(5, 7));
        endDay = Integer.valueOf(end_day.substring(8, 10));

        return endYear != startYear || startMonth != endMonth || startDay != endDay;

    }

    public static int daysBetween(Date dateFrom, Date dateEnd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFrom);
        long time1 = cal.getTimeInMillis();
        cal.setTime(dateEnd);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 22);
        int m = Integer.parseInt(String.valueOf(between_days));
        return m;
    }

    public static boolean isNight() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        return hour >= 2 && hour <= 5;
    }
}
