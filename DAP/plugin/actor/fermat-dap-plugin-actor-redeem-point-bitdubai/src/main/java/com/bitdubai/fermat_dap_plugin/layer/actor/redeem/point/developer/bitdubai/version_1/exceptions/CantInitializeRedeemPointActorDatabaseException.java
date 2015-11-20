package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions;


import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Nerio on 17/09/15.
 */
public class CantInitializeRedeemPointActorDatabaseException extends DAPException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE REQUESTED REDEEM POINT ACTOR DATABASE EXCEPTION";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantInitializeRedeemPointActorDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeRedeemPointActorDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeRedeemPointActorDatabaseException(final String message) {
        this(message, null);
    }


    public CantInitializeRedeemPointActorDatabaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantInitializeRedeemPointActorDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
