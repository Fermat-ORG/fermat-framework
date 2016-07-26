package com.bitdubai.fermat_api.layer.all_definition.util;


import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.ObjectNotSetException;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Clase Validate: clase creada principalmente con el fin de
 * hacer más legible el código, minimizar los {@link NullPointerException},
 * agilizar el proceso de desarrollo y mostrar valores más amigables
 * para el usuario en lugar de un "null" o un espacio vacío.
 * Todos los métodos públicos de esta clase son estáticos con el fin
 * de reducir las referencias innecesarias de objetos, ya que no hay
 * ningún motivo para tener que instanciarla.
 * <p></p>
 * Class made with the purpose of write most legible code, minimize
 * the number of NullPointerException, optimize the development process
 * and show the final user more friendly values instead of "null" or an
 * empty space.
 * All the public methods on this class are static with the goal of
 * minimize the innecesary object references, there's no reason for
 * create an instance of this class.
 *
 * @author Víctor A. Mars M.
 *         ****************************************************************************************************************
 */
public final class Validate {

    private Validate() {
        throw new AssertionError(); //NO INSTANCES.
    }

    /**
     * Chosen email pattern, with this pattern an mail
     * is considered "valid" when it meets the minimum
     * conditions: "aaa@aaa.aa".
     */
    public static final String EMAIL_PATTERN = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,3})$";
    /**
     * The min acceptable date for most SQL Systems.
     * Introducing a lower date most of the times results in an SQL
     * Exception and data loss.
     */
    public static final long MIN_DATE = -6847786800000L; //01/01/1753 00:00:00.000
    /**
     * The max acceptable date for most SQL Systems.
     * Introducing a higher date most of the times results in an SQL
     * Exception and data loss.
     */
    public static final long MAX_DATE = 253402318799999L; //31/12/9999 23:59:59.999

    public static final int MAX_SIZE_STRING_COLUMN = 1000000000; //Even when the max size for a string column is 2147483647 the default implementation is 1000000000.
    /*
     *
     * DEFAULT VALUES
     */
    public static final String DEFAULT_STRING = "Unknown";
    public static final Number DEFAULT_NUMBER = 0;
    public static final Date DEFAULT_DATE = new Date(MIN_DATE);

    public static boolean isObjectNull(Object o) {
        return o == null;
    }

    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_PATTERN, email);
    }

    public static boolean isValidString(String string) {
        return !(isObjectNull(string) || string.trim().isEmpty());
    }

    public static boolean isNonEmptyDate(Date date) {
        return !(isObjectNull(date));
    }

    public static boolean isValidNumber(Number number) {
        return !(isObjectNull(number));
    }

    public static boolean isNumberPositive(Number number) {
        return !(!isValidNumber(number) || number.doubleValue() < 0);
    }

    public static boolean isValidDate(Date date) {
        return !(isObjectNull(date) || date.after(new Date(MAX_DATE)) || date.before(new Date(MIN_DATE)));
    }

    public static boolean isDateAfterNow(Date date) {
        return (date.after(new Date()));
    }

    public static boolean isDateAfterToday(Date date) {
        return (date.after(today()));
    }

    public static boolean isDateToday(Date date) {
        return today().equals(dateWithoutTime(date.getTime()));
    }

    public static boolean isDateBeforeNow(Date date) {
        return (date.before(new Date()));
    }

    public static boolean isDateBeforeToday(Date date) {
        return (date.before(today()));
    }

    public static String verifyString(String string) {
        if (isValidString(string)) return string;
        else return DEFAULT_STRING;
    }

    public static Number verifyNumber(Number number) {
        if (isValidNumber(number)) return number;
        else return DEFAULT_NUMBER;
    }

    public static Date verifyDate(Date date) {
        if (isValidDate(date)) return date;
        else return DEFAULT_DATE;
    }

    public static <T> T verifySetter(T objectToSet, String message) throws CantSetObjectException {
        if (isObjectNull(objectToSet)) {
            throw new CantSetObjectException(message);
        }
        return objectToSet;
    }

    public static <T> T verifyGetter(T objectToGet, String message) throws ObjectNotSetException {
        if (isObjectNull(objectToGet)) {
            throw new ObjectNotSetException(message);
        } else return objectToGet;
    }

    public static <T> List<T> verifyGetter(List<T> objectToGet) {
        if (isObjectNull(objectToGet)) {
            return Collections.EMPTY_LIST;
        } else return objectToGet;
    }


    private static Date today() {
        return dateWithoutTime(System.currentTimeMillis());
    }

    private static Date dateWithoutTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
