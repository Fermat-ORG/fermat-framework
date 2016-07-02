package com.bitdubai.fermat_cbp_api.all_definition.util;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantGetUTCException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

/**
 * This class can provide a Date object with universal time reference
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 02/07/16.
 */
public class UniversalTime {

    /**
     * In this version this is the only api that we will consult, it can be changed in the future.
     * We are using http://www.timeapi.org/ for consult UTC
     */
    private static final String TIME_ORG_URL = "http://www.timeapi.org/utc/now";

    /**
     * This method returns a Date object with the local time based on UTC.
     * @return
     * @throws CantGetUTCException
     */
    public static Date getLocatedUniversalTime() throws CantGetUTCException{
        //We get the UTCString
        String utcString = getUTCDateStringFromExternalURL();
        return getLocalDateFromUTCDateString(utcString);
    }

    /**
     * This method parse a String UTC date in a Date object with local time as reference.
     * Please, be sure that the string must respect this format:
     * <b>2016-07-02T16:33:28+01:00</b>
     * Try to use the String that provides from the <code>getUTCDateStringFromExternalURL()</code> method.
     * @param utcString
     * @return
     * @throws CantGetUTCException
     */
    public static Date getLocalDateFromUTCDateString(String utcString) throws CantGetUTCException{
        try{
            //We are going to transform the previous string
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
            //We set the UTC in the previous String
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            //We need to remove this T, is not part of SimpleDateFormat allowed characters
            utcString = utcString.replace("T", "");
            //Generate Date object
            Date date = simpleDateFormat.parse(utcString);
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
     * @return
     * @throws CantGetUTCException
     */
    public static Date getUTC() throws CantGetUTCException{
        //We get the UTCString
        String utcString = getUTCDateStringFromExternalURL();
        return getUTCDateFromUTCDateString(utcString);
    }

    /**
     * This method parse a String UTC date in a Date object with UTC as reference.
     * Please, be sure that the string must respect this format:
     * <b>2016-07-02T16:33:28+01:00</b>
     * Try to use the String that provides from the <code>getUTCDateStringFromExternalURL()</code> method.
     * @param utcString
     * @return
     * @throws CantGetUTCException
     */
    public static Date getUTCDateFromUTCDateString(String utcString) throws CantGetUTCException{
        try{
            //We are going to transform the previous string
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
            //We need to remove this T, is not part of SimpleDateFormat allowed characters
            utcString = utcString.replace("T", "");
            //Generate Date object
            Date date = simpleDateFormat.parse(utcString);
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
     * @return
     * @throws CantGetUTCException
     */
    public static String getUTCDateStringFromExternalURL() throws CantGetUTCException {
        try{
            //Create URL
            URL url=new URL(TIME_ORG_URL);
            //Consult API
            Scanner scan = new Scanner(url.openStream());
            String stringDate = new String();
            //Creating String
            while (scan.hasNext())
                stringDate += scan.nextLine();
            scan.close();
            return stringDate;
        } catch (MalformedURLException e) {
            throw new CantGetUTCException(
                    FermatException.wrapException(e),
                    "Getting UTC date string from external API",
                    "The URL "+TIME_ORG_URL+" is malformed");
        } catch (IOException e) {
            throw new CantGetUTCException(
                    FermatException.wrapException(e),
                    "Getting UTC date string from external API",
                    "IO exception");
        }
    }

}
