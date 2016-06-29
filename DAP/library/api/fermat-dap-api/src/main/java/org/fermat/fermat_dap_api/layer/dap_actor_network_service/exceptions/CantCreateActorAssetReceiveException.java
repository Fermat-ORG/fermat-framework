package org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by loui on 22/02/15.
 */
public class CantCreateActorAssetReceiveException extends FermatException {

    /**
     *
     */
    private static final long serialVersionUID = 7137746546837677675L;

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE AACTOR ASSET RECEIVE";

    public CantCreateActorAssetReceiveException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateActorAssetReceiveException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateActorAssetReceiveException(final String message) {
        this(message, null);
    }

    public CantCreateActorAssetReceiveException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateActorAssetReceiveException() {
        this(DEFAULT_MESSAGE);
    }
}
