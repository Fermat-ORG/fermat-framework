package com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.request.crypto_payment.developer.bitdubai.version_1.exceptions.CantExecuteUnfinishedActionsException</code>
 * is thrown when there is an error trying to execute unfinished actions.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/10/2015.
 */
public class CantExecuteUnfinishedActionsException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T EXECUTE UNFINISHED ACTIONS EXCEPTION";

    public CantExecuteUnfinishedActionsException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantExecuteUnfinishedActionsException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantExecuteUnfinishedActionsException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
