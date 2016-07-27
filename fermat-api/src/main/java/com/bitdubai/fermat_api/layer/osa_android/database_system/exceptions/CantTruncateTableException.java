package com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantTruncateTableException</code>
 * is thrown when there is an error trying to truncate a database table.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantTruncateTableException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CANT TRUNCATE A TABLE EXCEPTION";

    public CantTruncateTableException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantTruncateTableException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
