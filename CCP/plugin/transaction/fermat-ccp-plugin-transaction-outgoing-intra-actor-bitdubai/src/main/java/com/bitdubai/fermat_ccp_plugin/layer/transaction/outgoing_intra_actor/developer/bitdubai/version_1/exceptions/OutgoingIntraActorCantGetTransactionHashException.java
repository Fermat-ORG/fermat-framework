package com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_intra_actor.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 10/19/15.
 */
public class OutgoingIntraActorCantGetTransactionHashException extends FermatException {
    public OutgoingIntraActorCantGetTransactionHashException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
