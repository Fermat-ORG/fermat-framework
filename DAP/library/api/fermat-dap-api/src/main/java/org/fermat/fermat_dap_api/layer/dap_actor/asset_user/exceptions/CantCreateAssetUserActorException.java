package org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions;

/**
 * Created by loui on 22/02/15.
 */
public class CantCreateAssetUserActorException extends org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException {

    /**
     *
     */
    private static final long serialVersionUID = 7137746546837677675L;

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE ASSET USER ACTOR";

    public CantCreateAssetUserActorException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateAssetUserActorException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateAssetUserActorException(final String message) {
        this(message, null);
    }

    public CantCreateAssetUserActorException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantCreateAssetUserActorException() {
        this(DEFAULT_MESSAGE);
    }
}
