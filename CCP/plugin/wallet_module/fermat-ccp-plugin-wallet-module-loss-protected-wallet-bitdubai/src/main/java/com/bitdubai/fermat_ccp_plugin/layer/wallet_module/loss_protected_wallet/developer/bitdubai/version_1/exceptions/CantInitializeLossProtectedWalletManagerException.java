package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedWalletException;

/**
 * The Class <code>CantInitializeCryptoWalletManagerException</code>
 * is thrown when i cant initialize the CryptoWalletManager.
 * <p/>
 *
 * Created Natalia Cortez on 07/03/2016.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeLossProtectedWalletManagerException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE CRYPTO WALLET MANAGER EXCEPTION";

    public CantInitializeLossProtectedWalletManagerException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeLossProtectedWalletManagerException(final Exception cause, final String context, final String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantInitializeLossProtectedWalletManagerException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }
}
