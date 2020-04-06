package com.aiit.byh.service.common.utils.date;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author dsqin
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 获取日期字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String getDateString(Date date, String format) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(date);
    }

    /**
     * 获取日期字符串
     *
     * @param time
     * @param format
     * @return
     */
    public static String getDateString(double time, String format) {
        long timeStamp = Math.round(time);
        Date date = new Date(timeStamp);

        return getDateString(date, format);
    }

    /**
     * 获取某天的结束时间
     *
     * @param date
     * @return
     */
    public static Date getDayEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /**
     * 获取某月的结束时间
     *
     * @param date
     * @return
     */
    public static Date getMonthEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /**
     * 获取一天的开始时间
     *
     * @param date
     * @return
     */
    public static Date getDayStartTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /**
     * 获取某月的开始时间
     *
     * @param date
     * @return
     */
    public static Date getMonthStartTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static void main(String[] args) {
        System.out.println(getDayStartTime(DateUtils
                .parseDate("2017/7/2 23:12:21")));
    }

    /**
     * 解析日期
     *
     * @param dateString
     * @return
     */
    public static Date parseDate(String dateString) {
        try {
            return DateUtils.parseDate(dateString, new String[]{
                    "yyyyMMddHHmmssSSS", "yyyyMMddHHmmss",
                    "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss",
                    "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss SSS",
                    "yyyy-MM-dd HH:mm:ss.SSS", "HH:mm:ss", "yyyyMMdd",
                    "yyyy-MM-dd"});
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 获取日期的秒数
     *
     * @param date
     * @return
     */
    public static long getSecond(Date date) {
        Calendar cl = DateUtils.toCalendar(date);
        long hours = cl.get(Calendar.HOUR_OF_DAY);
        long minute = cl.get(Calendar.MINUTE);
        long second = cl.get(Calendar.SECOND);

        return hours * 3600 + minute * 60 + second;
    }

    /**
     * 对比日期(hh:mm:ss部分)
     *
     * @param date1
     * @param date2
     * @return if (date1 >= date2) return true else return false
     */
    public static boolean compareDateInHourMinSecond(Date date1, Date date2) {
        long d1 = getSecond(date1);
        long d2 = getSecond(date2);
        return d1 >= d2;
    }

    /**
     * 获取两个时间间隔的天数
     *
     * @param startDate
     * @return
     */
    public static long getDiffDays(Date startDate, Date endDate) {
        long difftime = endDate.getTime() - startDate.getTime();
        return difftime / (24L * 60L * 60L * 1000L);
    }

    /**
     * 获取两个时间间隔的秒数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDiffSeconds(Date startDate, Date endDate) {
        long diffTime = endDate.getTime() - startDate.getTime();
        return diffTime / 1000L;
    }

    public static long getTime(Date startDate, Date endDate) {
        return endDate.getTime() - startDate.getTime();
    }

    /**
     * 获取两个时间间隔的小时数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getDiffHours(Date startDate, Date endDate) {
        return getTime(startDate, endDate) / MILLIS_PER_HOUR;
    }

    /**
     * 格式化日期字符串
     *
     * @param dateString 日期字符串
     * @param format     日期格式
     * @return
     */
    public static String formatDateString(String dateString, String format) {
        if (StringUtils.isBlank(dateString) || StringUtils.isBlank(format)) {
            return dateString;
        }

        Date date = parseDate(dateString);

        return getDateString(date, format);
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static String getTimeStampString() {
        SimpleDateFormat f = new SimpleDateFormat(
                DateFormats.patterncnTimeStamp, Locale.CHINA);
        return (f.format(new Date()));
    }

    /**
     * 日期是否比当前时间早
     *
     * @param dateString 日期字符串
     * @return true:比当前时间早; false:比当前时间迟
     */
    public static boolean beforeNow(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            return false;
        }

        Date date = parseDate(dateString);

        return date.before(new Date());
    }

    /**
     * 日期是否比当前时间晚
     *
     * @param dateString 日期字符串
     * @return true:比当前时间晚; false:比当前时间早
     */
    public static boolean afterNow(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            return false;
        }

        Date date = parseDate(dateString);

        return date.after(new Date());
    }

    /**
     * 日期是否比当前时间早
     *
     * @param timestamp 时间戳，毫秒
     * @return true:比当前时间早; false:比当前时间迟
     */
    public static boolean beforeNow(long timestamp) {
        return timestamp < System.currentTimeMillis();
    }

    /**
     * 日期是否比当前时间晚
     *
     * @param timestamp 时间戳，毫秒
     * @return true:比当前时间晚; false:比当前时间早
     */
    public static boolean afterNow(long timestamp) {
        return timestamp > System.currentTimeMillis();
    }

    /**
     * 日期，增加一定的秒数
     *
     * @param dateString 日期字符串
     * @param amount     增加的秒数
     * @return
     */
    public static Date addSeconds(String dateString, int amount) {
        Date date = new Date();
        if (StringUtils.isNotBlank(dateString)) {
            date = parseDate(dateString);
        }
        return DateUtils.addSeconds(date, amount);
    }

    public static String dealDateFormat(String oldDateStr) {
        //此格式只有  jdk 1.7才支持  yyyy-MM-dd'T'HH:mm:ss.SSSXXX
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
        Date date = null;
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = df.parse(oldDateStr);

            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            Date date1 = df1.parse(date.toString());
            return df2.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df2.format(new Date());
    }

    /**
     * 距当前时间的天数
     * 时间差的毫秒数换算成天数，向上取整
     *
     * @param timestamp 时间戳，毫秒
     * @return 负数代表早于当前时间
     */
    public static long diffDay(long timestamp) {
        // 除以（60*60*24*1000）向上取整
        return (long) Math.ceil((timestamp - System.currentTimeMillis()) / 86400000d);
    }

    /**
     * 获取季度开始时间
     * @param date
     * @return
     */
    public static Date getQuarterStartTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;

        if (month >= 1 && month <= 3)
            cal.set(Calendar.MONTH, 0);
        else if (month >= 4 && month <= 6)
            cal.set(Calendar.MONTH, 3);
        else if (month >= 7 && month <= 9)
            cal.set(Calendar.MONTH, 6);
        else if (month >= 10 && month <= 12)
            cal.set(Calendar.MONTH, 9);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取季度结束时间
     * @param date
     * @return
     */
    public static Date getQuarterEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;

        if (month >= 1 && month <= 3) {
            cal.set(Calendar.MONTH, 2);
            cal.set(Calendar.DATE, 31);
        } else if (month >= 4 && month <= 6) {
            cal.set(Calendar.MONTH, 5);
            cal.set(Calendar.DATE, 30);
        } else if (month >= 7 && month <= 9) {
            cal.set(Calendar.MONTH, 8);
            cal.set(Calendar.DATE, 30);
        } else if (month >= 10 && month <= 12) {
            cal.set(Calendar.MONTH, 11);
            cal.set(Calendar.DATE, 31);
        }
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
