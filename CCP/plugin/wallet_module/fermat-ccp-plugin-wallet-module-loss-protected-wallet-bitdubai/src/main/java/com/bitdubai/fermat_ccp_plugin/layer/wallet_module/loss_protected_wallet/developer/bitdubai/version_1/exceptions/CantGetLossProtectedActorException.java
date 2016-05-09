package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedWalletException;

/**
 * The Class <code>CantCreateOrRegisterActorException</code>
 * is thrown when i cant get an actor
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 02/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantGetLossProtectedActorException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET LOSS PROTECTED ACTOR EXCEPTION";

    public CantGetLossProtectedActorException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
