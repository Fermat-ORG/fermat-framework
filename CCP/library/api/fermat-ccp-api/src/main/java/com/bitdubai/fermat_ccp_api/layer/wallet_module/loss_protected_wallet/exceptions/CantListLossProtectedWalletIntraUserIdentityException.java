package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * Created by natalia on 01/10/15.
 */
public class CantListLossProtectedWalletIntraUserIdentityException  extends LossProtectedWalletException{

    public static final String DEFAULT_MESSAGE = "CAN'T LIST INTRA USER IDENTITY EXCEPTION";

    public CantListLossProtectedWalletIntraUserIdentityException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantListLossProtectedWalletIntraUserIdentityException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantListLossProtectedWalletIntraUserIdentityException(final String message) {
        this(message, null);
    }

    public CantListLossProtectedWalletIntraUserIdentityException() {
        this(DEFAULT_MESSAGE);
    }
}


