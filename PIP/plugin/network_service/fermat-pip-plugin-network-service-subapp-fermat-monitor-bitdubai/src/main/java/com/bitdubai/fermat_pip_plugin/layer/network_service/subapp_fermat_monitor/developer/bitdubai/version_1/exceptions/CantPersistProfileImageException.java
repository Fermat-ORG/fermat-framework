package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 04/09/15.
 */
public class CantPersistProfileImageException extends FermatException {


    public CantPersistProfileImageException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }


}