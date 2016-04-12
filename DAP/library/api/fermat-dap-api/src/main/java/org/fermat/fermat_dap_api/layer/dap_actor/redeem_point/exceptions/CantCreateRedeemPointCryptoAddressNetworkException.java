package org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by penny on 26/02/16.
 */
public class CantCreateRedeemPointCryptoAddressNetworkException extends DAPException {
    public CantCreateRedeemPointCryptoAddressNetworkException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
