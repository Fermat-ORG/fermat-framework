package com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions;

/**
 * Created by natalia on 01/10/15.
 */
public class CantListCryptoWalletIntraUserIdentityException  extends CryptoWalletException{

    public static final String DEFAULT_MESSAGE = "CAN'T LIST INTRA USER IDENTITY EXCEPTION";

    public CantListCryptoWalletIntraUserIdentityException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListCryptoWalletIntraUserIdentityException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantListCryptoWalletIntraUserIdentityException(final String message) {
        this(message, null);
    }

    public CantListCryptoWalletIntraUserIdentityException() {
        this(DEFAULT_MESSAGE);
    }
}


