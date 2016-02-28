package com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by penny on 26/02/16.
 */
public class CantGetRedeemPointCryptoAddressListException extends DAPException {
    public CantGetRedeemPointCryptoAddressListException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
