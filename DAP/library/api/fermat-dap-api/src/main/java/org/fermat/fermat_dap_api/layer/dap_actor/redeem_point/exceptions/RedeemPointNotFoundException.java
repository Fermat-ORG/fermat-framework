package org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by penny on 19/02/16.
 */
public class RedeemPointNotFoundException extends FermatException {

    public static final String DEFAULT_MESSAGE = "Redeem point Not Found";

    public RedeemPointNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
