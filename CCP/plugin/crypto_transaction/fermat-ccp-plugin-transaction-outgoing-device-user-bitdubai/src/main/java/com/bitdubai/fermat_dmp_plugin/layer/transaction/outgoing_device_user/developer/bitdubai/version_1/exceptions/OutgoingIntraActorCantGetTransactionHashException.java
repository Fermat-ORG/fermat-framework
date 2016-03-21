package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 10/19/15.
 */
public class OutgoingIntraActorCantGetTransactionHashException extends FermatException {
    public OutgoingIntraActorCantGetTransactionHashException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
