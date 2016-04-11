package org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by penny on 19/02/16.
 */
public class CantUpdateRedeemPointException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error updating to the Actor redeem point";

    public CantUpdateRedeemPointException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
