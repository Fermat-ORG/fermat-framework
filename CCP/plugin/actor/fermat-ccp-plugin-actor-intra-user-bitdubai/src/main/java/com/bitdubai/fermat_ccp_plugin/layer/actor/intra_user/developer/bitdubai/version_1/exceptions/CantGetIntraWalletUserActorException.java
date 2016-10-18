package com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 11/08/15.
 */
public class CantGetIntraWalletUserActorException extends FermatException {

private static final String DEFAULT_MESSAGE = "Cant Get Intra Wallet User Actor";

    public CantGetIntraWalletUserActorException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetIntraWalletUserActorException(final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, null, context, possibleReason);
    }
}