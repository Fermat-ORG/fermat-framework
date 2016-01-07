package com.bitdubai.fermat_cer_api.layer.provider.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Alex on 5/1/2016.
 */
public class DateHelper {

    /**
     * This method computes the timestamp of the beginning of the day (UTC), given a random timestamp
     **/
    public static long getStandarizedTimestampFromTimestamp(long timestamp)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(timestamp * 1000L));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() / 1000L);
    }

    /**
     * This method computes the timestamp of the beginning of the day (UTC), given a random string yyyy-MM-dd
     **/
    public static long getTimestampFromDateString(String date) throws ParseException
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.setTime(sdf.parse(date));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() / 1000L);
    }

    public static String getDateStringFromTimestamp(long timestamp)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(timestamp * 1000L));
    }
}
