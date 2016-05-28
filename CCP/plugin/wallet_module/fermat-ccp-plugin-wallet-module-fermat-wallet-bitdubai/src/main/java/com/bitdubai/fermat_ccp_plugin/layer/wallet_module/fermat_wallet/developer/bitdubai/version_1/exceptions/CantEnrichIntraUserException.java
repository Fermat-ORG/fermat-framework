package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.fermat_wallet.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CryptoWalletException;

/**
 * The Class <code>CantEnrichIntraUserException</code>
 * is thrown when i cant enrich a crypto wallet intra user actor.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantEnrichIntraUserException extends CryptoWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T ENRICH INTRA USER EXCEPTION";

    public CantEnrichIntraUserException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
