package org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/09/15.
 */
public class CantDeliverDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error delivering the Asset Issuing Transaction database.";

    public CantDeliverDatabaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDeliverDatabaseException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
