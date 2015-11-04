package com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 10/19/15.
 */
public class OutgoingIntraActorCantGetSendCryptoTransactionHashException extends FermatException {
    public OutgoingIntraActorCantGetSendCryptoTransactionHashException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
