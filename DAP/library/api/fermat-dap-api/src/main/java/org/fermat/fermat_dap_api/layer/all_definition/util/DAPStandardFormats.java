package org.fermat.fermat_dap_api.layer.all_definition.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/01/16.
 */
public final class DAPStandardFormats implements Serializable {

    //VARIABLE DECLARATION

    public static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();
    public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static final DateFormat DATETIME_FORMAT = DateFormat.getDateTimeInstance();
    public static final SimpleDateFormat SIMPLE_DATETIME_FORMAT = new SimpleDateFormat("MMM d, yyyy HH:mm");
    public static final DecimalFormat BITCOIN_FORMAT = new DecimalFormat("0.000000");
    public static final NumberFormat EDIT_NUMBER_FORMAT = new DecimalFormat("##.######");
    public static final long MINIMUN_SATOSHI_AMOUNT = 50000;

    //CONSTRUCTORS

    private DAPStandardFormats() {
        throw new AssertionError("NO INSTANCES!!");
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
