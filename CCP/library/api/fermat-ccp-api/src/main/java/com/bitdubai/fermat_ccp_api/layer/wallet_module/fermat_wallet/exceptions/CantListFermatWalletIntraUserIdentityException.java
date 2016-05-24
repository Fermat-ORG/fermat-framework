package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions;

/**
 * Created by natalia on 01/10/15.
 */
public class CantListFermatWalletIntraUserIdentityException extends FermatWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T LIST INTRA USER IDENTITY EXCEPTION";

    public CantListFermatWalletIntraUserIdentityException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListFermatWalletIntraUserIdentityException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantListFermatWalletIntraUserIdentityException(final String message) {
        this(message, null);
    }

    public CantListFermatWalletIntraUserIdentityException() {
        this(DEFAULT_MESSAGE);
    }
}


