package org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 11/08/15.
 */
public class CantPersistProfileImageException extends FermatException {


    public CantPersistProfileImageException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }


}