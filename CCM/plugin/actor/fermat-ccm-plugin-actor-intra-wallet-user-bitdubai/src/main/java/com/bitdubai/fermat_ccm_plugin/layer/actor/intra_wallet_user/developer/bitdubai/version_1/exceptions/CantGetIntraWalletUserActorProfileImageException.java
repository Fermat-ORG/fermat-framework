package com.bitdubai.fermat_ccm_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 11/08/15.
 */
public class CantGetIntraWalletUserActorProfileImageException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET INTRA USER ACTOR PROFILE IMAGE EXCEPTION";

    public CantGetIntraWalletUserActorProfileImageException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetIntraWalletUserActorProfileImageException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}