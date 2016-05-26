package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.fermat_wallet.developer.bitdubai.version_1.exceptions;


import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions.FermatWalletException;

/**
 * The Class <code>CantInitializeCryptoWalletManagerException</code>
 * is thrown when i cant initialize the CryptoWalletManager.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCryptoWalletManagerException extends FermatWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE CRYPTO WALLET MANAGER EXCEPTION";

    public CantInitializeCryptoWalletManagerException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCryptoWalletManagerException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantInitializeCryptoWalletManagerException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }
}
