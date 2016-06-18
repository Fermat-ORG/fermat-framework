package org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressReceivedActionException</code>
 * is thrown when there is an error trying to handle a crypto address received action.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/11/2015.
 */
public class CantHandleCryptoAddressReceivedActionException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE CRYPTO ADDRESS RECEIVED ACTION EXCEPTION";

    public CantHandleCryptoAddressReceivedActionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHandleCryptoAddressReceivedActionException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantHandleCryptoAddressReceivedActionException(Exception cause, String context) {
        this(DEFAULT_MESSAGE, cause, context, null);
    }

}
