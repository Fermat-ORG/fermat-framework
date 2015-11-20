package com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.redeem_point.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 15/10/15.
 */
public class CantStartSubsystemException extends FermatException {
    static final String DEFAULT_MESSAGE = "ERROR STARTING DAPAssetRedeemPointActorNetworkServiceSubsystem.";

    public CantStartSubsystemException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
