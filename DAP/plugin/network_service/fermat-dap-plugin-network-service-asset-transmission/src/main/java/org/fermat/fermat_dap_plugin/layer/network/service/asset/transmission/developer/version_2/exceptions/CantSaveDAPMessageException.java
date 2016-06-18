package org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Jose Briceño josebricenor@gmail.com on 18/02/16.
 */
public class CantSaveDAPMessageException extends DAPException {
    public static final String DEFAULT_MESSAGE = "Cannot save the event.";

    public CantSaveDAPMessageException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
