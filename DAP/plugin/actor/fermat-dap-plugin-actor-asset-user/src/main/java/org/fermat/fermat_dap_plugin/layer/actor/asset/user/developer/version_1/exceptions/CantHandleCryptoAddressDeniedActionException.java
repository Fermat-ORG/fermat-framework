package org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressDeniedActionException</code>
 * is thrown when there is an error trying to handle a crypto address denied action.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 */
public class CantHandleCryptoAddressDeniedActionException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE CRYPTO ADDRESS DENIED ACTION EXCEPTION";

    public CantHandleCryptoAddressDeniedActionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHandleCryptoAddressDeniedActionException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantHandleCryptoAddressDeniedActionException(Exception cause, String context) {
        this(DEFAULT_MESSAGE, cause, context, null);
    }
}
