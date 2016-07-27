package com.bitdubai.fermat_cbp_api.all_definition.util;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetUTCException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;


/**
 * This class can provide a Date object with universal time reference.
 * <p/>
 * THE METHODS OF THIS CLASS NEED TO BE CONSUMED IN THE BACKGROUND THREAD
 * <p/>
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 02/07/16.
 */
public class UniversalTime {

    /**
     * In this version this is the only api that we will consult, it can be changed in the future.
     * We are using http://www.timeapi.org/ for consult UTC
     */
    private static final String TIME_ORG_URL = "http://www.timeapi.org/utc/now";

    private static final int DATE_INDEX = 0;

    private static final int ZONE_INDEX = 1;

    private static final String SECONDS_TO_ADD = ":00";

    private static final int HOUR_INDEX = 0;

    private static final int MINUTES_INDEX = 1;

    private static final int SECONDS_INDEX = 2;

    /**
     * This method returns a Date object with the local time based on UTC.
     *
     * @return
     * @throws CantGetUTCException
     */
    public static Date getLocatedUniversalTime() throws CantGetUTCException {
        //We get the UTCString
        String utcString = getUTCDateStringFromExternalURL();
        return getLocalDateFromUTCDateString(utcString);
    }

    /**
     * This method parse a String UTC date in a Date object with local time as reference.
     * Please, be sure that the string must respect this format:
     * <b>2016-07-02T16:33:28+01:00</b>
     * Try to use the String that provides from the <code>getUTCDateStringFromExternalURL()</code> method.
     *
     * @param utcString
     * @return
     * @throws CantGetUTCException
     */
    public static Date getLocalDateFromUTCDateString(String utcString) throws CantGetUTCException {
        try {
            //We are going to transform the previous string
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
            //We set the UTC in the previous String
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = parseStringToDate(simpleDateFormat, utcString);
            return date;
        } catch (ParseException e) {
            throw new CantGetUTCException(
                    FermatException.wrapException(e),
                    "Getting local Date from UTC String",
                    "Cannot parse String to Date");
        }
    }

    /**
     * This method returns the UTC Date.
     *
     * @return
     * @throws CantGetUTCException
     */
    public static Date getUTC() throws CantGetUTCException {
        //We get the UTCString
        String utcString = getUTCDateStringFromExternalURL();
        return getUTCDateFromUTCDateString(utcString);
    }

    /**
     * This method parse a String UTC date in a Date object with UTC as reference.
     * Please, be sure that the string must respect this format:
     * <b>2016-07-02T16:33:28+01:00</b>
     * Try to use the String that provides from the <code>getUTCDateStringFromExternalURL()</code> method.
     *
     * @param utcString
     * @return
     * @throws CantGetUTCException
     */
    public static Date getUTCDateFromUTCDateString(String utcString) throws CantGetUTCException {
        try {
            //We are going to transform the previous string
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
            Date date = parseStringToDate(simpleDateFormat, utcString);
            return date;
        } catch (ParseException e) {
            throw new CantGetUTCException(
                    FermatException.wrapException(e),
                    "Getting UTC Date from UTC String",
                    "Cannot parse String to Date");
        }
    }

    /**
     * This method returns a Sting with UTC
     *
     * @return
     * @throws CantGetUTCException
     */
    public static String getUTCDateStringFromExternalURL() throws CantGetUTCException {
        try {
            //Create URL
            URL url = new URL(TIME_ORG_URL);
            //Consult API
            Scanner scan = new Scanner(url.openStream());
            String stringDate = new String();
            //Creating String
            while (scan.hasNext())
                stringDate += scan.nextLine();
            scan.close();
            return fixDate(stringDate);
        } catch (MalformedURLException e) {
            throw new CantGetUTCException(
                    FermatException.wrapException(e),
                    "Getting UTC date string from external API",
                    new StringBuilder().append("The URL ").append(TIME_ORG_URL).append(" is malformed").toString());
        } catch (IOException e) {
            throw new CantGetUTCException(
                    FermatException.wrapException(e),
                    "Getting UTC date string from external API",
                    "IO exception");
        } catch (ParseException e) {
            throw new CantGetUTCException(
                    FermatException.wrapException(e),
                    "Getting UTC date string from external API",
                    "Cannot fix the date");
        }
    }

    /**
     * This method parse a string to Date
     *
     * @param simpleDateFormat
     * @param utcString
     * @return
     * @throws ParseException
     */
    private static Date parseStringToDate(
            SimpleDateFormat simpleDateFormat,
            String utcString)
            throws ParseException {
        //We need to remove this T, is not part of SimpleDateFormat allowed characters
        utcString = utcString.replace("T", "");
        //Generate Date object
        Date date = simpleDateFormat.parse(utcString);
        return date;
    }

    /**
     * This method fix the string date.
     *
     * @param stringDate
     * @return
     * @throws CantGetUTCException
     * @throws ParseException
     */
    private static String fixDate(String stringDate) throws CantGetUTCException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        if (stringDate.contains("+")) {
            return restTime(simpleDateFormat, stringDate);
        }
        //In this version I'll remove this case
        /*if(stringDate.contains("-")){
            return addTime(simpleDateFormat, stringDate);
        }*/
        //In this case, we don't required to do nothing
        return stringDate;
    }

    //TODO: optimize these methods

    /**
     * This method add additional time to a string date
     *
     * @param simpleDateFormat
     * @param stringDate
     * @return
     * @throws ParseException
     * @throws CantGetUTCException
     */
    private static String restTime(SimpleDateFormat simpleDateFormat, String stringDate)
            throws ParseException,
            CantGetUTCException {
        String[] arrayDate = stringDate.split("\\+");
        if (arrayDate.length == 2) {
            String time = arrayDate[DATE_INDEX];
            String zone = arrayDate[ZONE_INDEX] + SECONDS_TO_ADD;
            int[] diffArray = parseStringToIntegerArray(zone);
            Date originalDate = parseStringToDate(simpleDateFormat, time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(originalDate);
            //Fixing time process
            calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - diffArray[HOUR_INDEX]);
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - diffArray[MINUTES_INDEX]);
            calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - diffArray[SECONDS_INDEX]);
            Date fixedDate = calendar.getTime();
            return simpleDateFormat.format(fixedDate);
        } else {
            throw new CantGetUTCException("Cannot fix the date format");
        }
    }

    /**
     * This method returns an integer array based in stringToParse
     * If there any kind of error, the value is set to 0
     *
     * @param stringToParse
     * @return
     */
    private static int[] parseStringToIntegerArray(String stringToParse) {
        String[] array = stringToParse.split(":");
        int[] resultArray = new int[array.length];
        int counter = 0;
        for (String element : array) {
            try {
                resultArray[counter] = Integer.valueOf(element);
            } catch (Exception e) {
                resultArray[counter] = 0;
            }
            counter++;
        }
        return resultArray;
    }

    /**
     * This method returns remove time to a string date
     * @param simpleDateFormat
     * @param stringDate
     * @return
     * @throws ParseException
     * @throws CantGetUTCException
     */
    /*private static String addTime(SimpleDateFormat simpleDateFormat, String stringDate)
            throws ParseException,
            CantGetUTCException {
        String[] arrayDate = stringDate.split("\\-");
        if(arrayDate.length==2){
            String time = arrayDate[DATE_INDEX];
            String zone = arrayDate[ZONE_INDEX]+SECONDS_TO_ADD;
            Date originalDate = parseStringToDate(simpleDateFormat, time);
            Date differentialDate = parseStringToDate(simpleDateFormat, time.substring(0, 10) + zone);
            long originalTime = originalDate.getTime();
            long differentialTime = originalTime - differentialDate.getTime();
            long finalTime = originalTime + differentialTime;
            Date finalDate = new Date(finalTime);
            return simpleDateFormat.format(finalDate);
        } else{
            throw new CantGetUTCException("Cannot fix the date format");
        }
    }*/

}
