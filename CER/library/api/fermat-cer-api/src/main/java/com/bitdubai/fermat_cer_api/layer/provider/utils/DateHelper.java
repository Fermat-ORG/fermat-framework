package com.bitdubai.fermat_cer_api.layer.provider.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Alex on 5/1/2016.
 */
public class DateHelper {

    /**
     * This method computes the timestamp of the beginning of the day (UTC), given a random timestamp
     **/
    public static long getDaysUTCTimestampFromTimestamp(long timestamp)
    {
        //Compute UTC start of the day for the given timestamp
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTime(new Date(timestamp));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static String getDateStringFromTimestamp(long timestamp)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(timestamp));
    }
}
