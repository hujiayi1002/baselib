package com.ocse.baseandroid.utils;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Description：
 * <br/>
 * Author: reese
 * <br/>
 * CreateTime: 2020-01-05 18:12
 * <br/>
 * Email: reese@jiuhuar.com
 */
public class DateUtils {
    /**
     * 缺省的日期显示格式： yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_FORMAT_POINT = "yyyy.MM.dd";

    /**
     * 缺省的日期时间显示格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_COMMON_DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static final String DEFAULT_DATE_YY_MM_DD = "MM/dd HH:mm";

    private static final SimpleDateFormat sFormatMessageToday = new SimpleDateFormat("今天");
    private static final SimpleDateFormat sFormatToday = new SimpleDateFormat("今天 HH:mm");
    private static final SimpleDateFormat sFormatHourMinute = new SimpleDateFormat("HH:mm");

    private static final SimpleDateFormat sFormatThisYear = new SimpleDateFormat("MM/dd HH:mm");
    private static final SimpleDateFormat sFormatOtherYear = new SimpleDateFormat("yy/MM/dd HH:mm");
    private static final SimpleDateFormat sFormatMessageThisYear = new SimpleDateFormat("MM/dd");
    private static final SimpleDateFormat sFormatMessageOtherYear = new SimpleDateFormat("yy/MM/dd");


    private static final SimpleDateFormat sFormatThisYearNoHour = new SimpleDateFormat("MM/dd");
    private static final SimpleDateFormat sFormatOtherYearNoHour = new SimpleDateFormat("yy/MM/dd");

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatDate(Date date) throws ParseException {
        synchronized (sdf) {
            return sdf.format(date);
        }
    }

    public static Date parse(String strDate) throws ParseException {
        synchronized (sdf) {
            return sdf.parse(strDate);
        }
    }

    public static String formatTodayDate(Date date) throws ParseException {
        synchronized (sFormatToday) {
            return sFormatToday.format(date);
        }
    }

    public static Date parseToday(String strDate) throws ParseException {
        synchronized (sFormatToday) {
            return sFormatToday.parse(strDate);
        }
    }


    public static String formatThisYearDate(Date date) throws ParseException {
        synchronized (sFormatThisYear) {
            return sFormatThisYear.format(date);
        }
    }

    public static Date parseThisYear(String strDate) throws ParseException {
        synchronized (sFormatThisYear) {
            return sFormatThisYear.parse(strDate);
        }
    }


    public static String formatOtherYearDate(Date date) throws ParseException {
        synchronized (sFormatOtherYear) {
            return sFormatOtherYear.format(date);
        }
    }

    public static Date parseOtherYear(String strDate) throws ParseException {
        synchronized (sFormatOtherYear) {
            return sFormatOtherYear.parse(strDate);
        }
    }

    public static String formatMessageToday(Date date) throws ParseException {
        synchronized (sFormatMessageToday) {
            return sFormatMessageToday.format(date);
        }
    }

    public static Date parseMessageToday(String strDate) throws ParseException {
        synchronized (sFormatMessageToday) {
            return sFormatMessageToday.parse(strDate);
        }
    }

    public static String formatMessageThisYear(Date date) throws ParseException {
        synchronized (sFormatMessageThisYear) {
            return sFormatMessageThisYear.format(date);
        }
    }

    public static Date parseMessageThisYear(String strDate) throws ParseException {
        synchronized (sFormatMessageThisYear) {
            return sFormatMessageThisYear.parse(strDate);
        }
    }

    public static String formatMessageOtherYear(Date date) throws ParseException {
        synchronized (sFormatMessageOtherYear) {
            return sFormatMessageOtherYear.format(date);
        }
    }

    public static Date parseMessageOtherYear(String strDate) throws ParseException {
        synchronized (sFormatMessageOtherYear) {
            return sFormatMessageOtherYear.parse(strDate);
        }
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getYear() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        return year;
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        Date d = new Date(time);

        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT, Locale.getDefault());
        return format.format(d);
    }

    /**
     * 转换时间为yyyy-MM-dd HH:mm格式
     *
     * @param time
     * @return
     */
    public static String getDateToRomanString(long time) {
        Date d = new Date(time);

        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_COMMON_DATETIME_FORMAT, Locale.getDefault());
        return format.format(d);
    }

    /*时间戳转换成字符窜*/
    public static String getActDateToString(long time) {
        Date d = new Date(time);
        return new SimpleDateFormat(DEFAULT_DATE_YY_MM_DD, Locale.getDefault()).format(d);
    }

    public static String getTimeWithoutHour(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault());
        return format.format(date);
    }

    /**
     * YY-MM-dd
     *
     * @param timeMill 毫秒单位
     * @return
     */
    public static String getDateFromStamp(long timeMill) {
        Date d = new Date(timeMill);
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault());
        return format.format(d);
    }

    /**
     * 日期字符串转时间戳
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static long dateToStamp(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault());
        try {
            return format.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date().getTime(); // 默认当天
    }

    /**
     * YY-MM-dd
     *
     * @param time 秒单位
     * @return
     */
    public static String getDateWithoutHourByPoint(long time) {
        long timeMill = time * 1000; // 转成毫秒
        Date d = new Date(timeMill);
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT_POINT, Locale.getDefault());
        return format.format(d);
    }

    /**
     * 拼接起始和结束时间
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getStartAndEndTimeRang(long startTime, long endTime) {
        String startStr = getDateWithoutHourByPoint(startTime);
        String endStr = getDateWithoutHourByPoint(endTime);
        return startStr + "-" + endStr;
    }

    public static String dayToNow(long time, Context context) {
        return dayToNow(time, true, context);
    }


    public static String dayToTime(long time, Context context) {
        return dayToTime(time, true, context);
    }

    public static String dayToNow(long time, boolean displayHour, Context context) {
        long timeMill = time * 1000;
        long nowMill = System.currentTimeMillis();

        long minute = (nowMill - timeMill) / 60000;
        if (minute < 60) {
            if (minute <= 0) {
                int second = (int) Math.max((nowMill - timeMill) / 1000, 1);
                // 由于手机时间的原因，有时候会为负，这时候显示1秒前
                return second + "秒前";
            } else {
                return (int) (minute) + "分钟前";
            }
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timeMill);
        int year = calendar.get(GregorianCalendar.YEAR);
        int month = calendar.get(GregorianCalendar.MONTH);
        int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);

        calendar.setTimeInMillis(nowMill);
        Long timeObject = timeMill;
        if (calendar.get(GregorianCalendar.YEAR) != year) { // 不是今年
            SimpleDateFormat formatOtherYear = displayHour ? sFormatOtherYear : sFormatMessageOtherYear;
            formatOtherYear.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            return formatOtherYear.format(timeObject);
        } else if (calendar.get(GregorianCalendar.MONTH) != month
                || calendar.get(GregorianCalendar.DAY_OF_MONTH) != day) { // 今年
            SimpleDateFormat formatThisYear = displayHour ? sFormatThisYear : sFormatMessageThisYear;
            formatThisYear.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            return formatThisYear.format(timeObject);
        } else { // 今天
            SimpleDateFormat formatToday = sFormatHourMinute;
            formatToday.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            return formatToday.format(timeObject);
        }
    }

    public static String dayToTime(long time, boolean displayHour, Context context) {
        long timeMill = time * 1000;
        long nowMill = System.currentTimeMillis();

        long minute = (nowMill - timeMill) / 60000;
        if (minute < 60) {
            if (minute <= 0) {
                int second = (int) Math.max((nowMill - timeMill) / 1000, 1);
                // 由于手机时间的原因，有时候会为负，这时候显示1秒前
                return second + "秒前";
            } else {
                return (int) (minute) + "分钟前";
            }
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timeMill);
        int year = calendar.get(GregorianCalendar.YEAR);
        int month = calendar.get(GregorianCalendar.MONTH);
        int day = calendar.get(GregorianCalendar.DAY_OF_MONTH);

        calendar.setTimeInMillis(nowMill);
        Long timeObject = timeMill;
        if (calendar.get(GregorianCalendar.YEAR) != year) { // 不是今年
            SimpleDateFormat formatOtherYear = displayHour ? sFormatOtherYearNoHour : sFormatMessageOtherYear;
            formatOtherYear.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            return formatOtherYear.format(timeObject);
        } else if (calendar.get(GregorianCalendar.MONTH) != month
                || calendar.get(GregorianCalendar.DAY_OF_MONTH) != day) { // 今年
            SimpleDateFormat formatThisYear = displayHour ? sFormatThisYearNoHour : sFormatMessageThisYear;
            formatThisYear.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            return formatThisYear.format(timeObject);
        } else { // 今天
            SimpleDateFormat formatToday = displayHour ? sFormatToday : sFormatMessageToday;
            formatToday.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            return formatToday.format(timeObject);
        }
    }

    /**
     * 返回过去一天/一周/一月/一年的起始事件
     *
     * @param timeMarking 时间标识：-1 昨天；1 当天；2 一周；3 一月；4 一年
     * @return time
     */
    public static long getTargetNodeZeroStamp(int timeMarking) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (timeMarking == -1) {
            calendar.add(Calendar.DATE, -1); // 昨天
            return calendar.getTimeInMillis();
        }

        if (timeMarking == 1) {
            calendar.add(Calendar.DATE, 0); // 当天
        } else if (timeMarking == 2) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
            }
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else if (timeMarking == 3) {
            calendar.set(Calendar.DAY_OF_MONTH, 1); // 本月
        } else if (timeMarking == 4) { // 本年度
            calendar.set(Calendar.DATE, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
        }
        return calendar.getTimeInMillis();
    }

    /**
     * 获取昨天23点59分59秒
     *
     * @return
     */
    public static long getYesterdayEndStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, -1);
        return calendar.getTimeInMillis();
    }

    /**
     * 是否在指定指定时间内,24小时制
     *
     * @return
     */
    public static boolean inLimitedTime(int startHour, int endHour) {
        SimpleDateFormat hh = new SimpleDateFormat("HH", Locale.CHINA);
        SimpleDateFormat mm = new SimpleDateFormat("mm", Locale.CHINA);
        // 获取指定时区的时间
        hh.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        mm.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        Date date = new Date();
        String hour = hh.format(date);
        String minute = mm.format(date);
        final int start = startHour * 60;
        final int end = endHour * 60;
        int minuteOfDay = Integer.parseInt(hour) * 60 + Integer.parseInt(minute);
        Log.i("stf", "--hour:minute-->" + hour + ":" + minute);
        return minuteOfDay >= start && minuteOfDay <= end;
    }
}