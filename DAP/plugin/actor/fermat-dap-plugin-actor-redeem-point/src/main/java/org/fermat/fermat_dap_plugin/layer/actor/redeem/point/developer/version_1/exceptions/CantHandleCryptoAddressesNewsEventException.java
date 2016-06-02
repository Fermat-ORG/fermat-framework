package org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantHandleCryptoAddressesNewsEventException</code>
 * is thrown when there is an error trying to handle a crypto address news event.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/11/2015.
 */
public class CantHandleCryptoAddressesNewsEventException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T HANDLE CRYPTO ADDRESSES NEWS EVENT EXCEPTION";

    public CantHandleCryptoAddressesNewsEventException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantHandleCryptoAddressesNewsEventException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
