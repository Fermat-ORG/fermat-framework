package com.bitdubai.fermat_cbp_plugin.layer.middleware.customers.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.middleware.customers.developer.bitdubai.version_1.exceptions.CantInitializeCustomersMiddlewareDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Angel Veloz - (vlzangel91@gmail.com) on 28/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCustomersMiddlewareDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE CUSTOMERS MIDDLEWARE DATABASE EXCEPTION";

    public CantInitializeCustomersMiddlewareDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCustomersMiddlewareDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCustomersMiddlewareDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCustomersMiddlewareDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}