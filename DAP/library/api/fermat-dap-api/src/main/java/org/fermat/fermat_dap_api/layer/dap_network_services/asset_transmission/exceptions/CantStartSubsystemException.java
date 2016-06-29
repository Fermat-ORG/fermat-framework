package org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 11/09/15.
 */
public class CantStartSubsystemException extends FermatException {
    static final String DEFAULT_MESSAGE = "ERROR STARTING DAPAssetTransmissionNetworkServiceSubsystem.";

    public CantStartSubsystemException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
