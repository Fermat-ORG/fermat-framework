package com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Nerio on 02/11/15.
 */
public class CantConnectToActorAssetRedeemPointException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error connecting to the Actor Asset Redeem Point";
    public CantConnectToActorAssetRedeemPointException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
