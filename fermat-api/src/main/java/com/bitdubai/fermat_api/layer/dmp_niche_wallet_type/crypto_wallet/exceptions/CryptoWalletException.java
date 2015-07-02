package com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exception.WalletContactsException</code>
 * is thrown when there's an exception in NicheWalletTypeCryptoWallet Plugin.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/07/15.
 * @version 1.0
 */
public class CryptoWalletException extends FermatException {

    public static final String DEFAULT_MESSAGE = "THE NICHE WALLET TYPE CRYPTO WALLET HAS TRIGGERED AN EXCEPTION";

    public CryptoWalletException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CryptoWalletException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CryptoWalletException(final String message) {
        this(message, null);
    }

    public CryptoWalletException() {
        this(DEFAULT_MESSAGE);
    }
}
