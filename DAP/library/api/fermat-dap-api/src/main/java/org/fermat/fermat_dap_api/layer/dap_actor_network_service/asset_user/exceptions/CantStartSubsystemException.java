package org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 11/09/15.
 */
public class CantStartSubsystemException extends FermatException {
    static final String DEFAULT_MESSAGE = "ERROR STARTING DAPAssetUserActorNetworkServiceSubsystem.";

    public CantStartSubsystemException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
