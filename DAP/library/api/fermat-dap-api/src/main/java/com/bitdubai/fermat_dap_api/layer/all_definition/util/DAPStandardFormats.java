package com.bitdubai.fermat_dap_api.layer.all_definition.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/01/16.
 */
public final class DAPStandardFormats {

    //VARIABLE DECLARATION

    public static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();
    public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static final DateFormat DATETIME_FORMAT = DateFormat.getDateTimeInstance();
    public static final DecimalFormat BITCOIN_FORMAT = new DecimalFormat("0.000000");

    //CONSTRUCTORS

    private DAPStandardFormats() {
        throw new AssertionError("NO INSTANCES!!");
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
