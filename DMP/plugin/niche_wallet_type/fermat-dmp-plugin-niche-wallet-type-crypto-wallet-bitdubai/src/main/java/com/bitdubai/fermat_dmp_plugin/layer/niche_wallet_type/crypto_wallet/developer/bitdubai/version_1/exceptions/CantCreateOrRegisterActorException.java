package com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CryptoWalletException;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.exceptions.CantCreateOrRegisterActorException</code>
 * is thrown when i cant create or register an actor.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantCreateOrRegisterActorException extends CryptoWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T CREATE OR REGISTER ACTOR EXCEPTION";

    public CantCreateOrRegisterActorException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantCreateOrRegisterActorException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantCreateOrRegisterActorException(final String message) {
        this(message, null);
    }

    public CantCreateOrRegisterActorException() {
        this(DEFAULT_MESSAGE);
    }
}
