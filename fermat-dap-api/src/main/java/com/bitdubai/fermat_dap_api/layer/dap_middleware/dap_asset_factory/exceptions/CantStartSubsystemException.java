package com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 10/09/15.
 */
public class CantStartSubsystemException extends FermatException {
    static final String DEFAULT_MESSAGE = "There was an error staring the AssetFactory subsystem.";

    public CantStartSubsystemException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
