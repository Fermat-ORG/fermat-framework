package com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException</code>
 * is thrown when an error occurs trying to get a wallet contact registry
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantGetWalletContactRegistryException extends WalletContactsException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET WALLET CONTACT REGISTRY EXCEPTION";

    public CantGetWalletContactRegistryException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetWalletContactRegistryException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetWalletContactRegistryException(final String message) {
        this(message, null);
    }

    public CantGetWalletContactRegistryException() {
        this(DEFAULT_MESSAGE);
    }
}
