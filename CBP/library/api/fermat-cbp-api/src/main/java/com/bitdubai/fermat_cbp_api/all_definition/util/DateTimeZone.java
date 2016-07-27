package com.bitdubai.fermat_cbp_api.all_definition.util;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetUTCException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Yordin Alayn on 03.07.16.
 */
public class DateTimeZone {

    private String timeZone;
    private String dateTime;
    private String formDate;

    public DateTimeZone(String timeZone, String dateTime, String formDate) {
        this.timeZone = timeZone;
        this.formDate = formDate;
        this.dateTime = dateTime;
    }

    public DateTimeZone(String timeZone, long dateTime, String formDate) {
        this.timeZone = timeZone;
        this.formDate = formDate;
        this.dateTime = getDateTimeToLong(dateTime);
    }

    /**
     * This method returns a date in String of the time zone indicate
     *
     * @return to String of date
     */
    public String getDate() {

        Date parsed = getDateTimeDefaul(TimeZone.getDefault());
        SimpleDateFormat destFormt = getDateTimeZone(this.timeZone);
        return destFormt.format(parsed);

    }

    /**
     * This method returns a date in String of the time zone indicate
     *
     * @param timeZone
     * @return to String of date
     */
    public String getDate(TimeZone timeZone) {

        Date parsed = getDateTimeDefaul(timeZone);
        SimpleDateFormat destFormt = getDateTimeZone(this.timeZone);
        return destFormt.format(parsed);

    }

    public String getDateUTC() {

        String timeZoneUTC = TimeZone.getTimeZone("UTC").getID();

        Date parsed = getDateTimeDefaul(TimeZone.getDefault());
        SimpleDateFormat destFormt = getDateTimeZone(timeZoneUTC);
        return destFormt.format(parsed);

    }

    /**
     * This method returns a date Today UTC in String
     *
     * @return to String of date UTC
     */
    public String getDateTodayUTC() {

        String dateText = "";

        try {

            Date dateTimeToday = UniversalTime.getUTC();
            SimpleDateFormat df2 = new SimpleDateFormat(this.formDate);
            dateText = df2.format(dateTimeToday);

        } catch (CantGetUTCException e) {
            System.out.print("CBP-API-DateTimeZone: ERROR GET DATE TODAY IN UTC");
        }

        return dateText;
    }

    /**
     * This method returns a date in String From a long
     *
     * @param dateTime
     * @return to String of date
     */
    private String getDateTimeToLong(long dateTime) {

        Date date = new Date(dateTime);
        SimpleDateFormat df2 = new SimpleDateFormat(this.formDate);
        String dateText = df2.format(date);

        return dateText;
    }

    /**
     * This method returns a date in time zone default
     *
     * @return to Date in Time Zone Default
     */
    private Date getDateTimeDefaul(TimeZone timeZone) {

        Date parsed = null;

        try {

            SimpleDateFormat sourceFormat = new SimpleDateFormat(this.formDate);
            sourceFormat.setTimeZone(timeZone);
            parsed = sourceFormat.parse(this.dateTime);

        } catch (ParseException e) {
            System.out.print("CBP-API-DateTimeZone: ERROR IN PARSE DATE");
        }

        return parsed;

    }

    private SimpleDateFormat getDateTimeZone(String timeZone) {

        TimeZone tz = TimeZone.getTimeZone(timeZone);
        SimpleDateFormat destFormat = new SimpleDateFormat(this.formDate);
        destFormat.setTimeZone(tz);

        return destFormat;

    }
}
