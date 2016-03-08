package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions;

/**
 * The eXCEPTION <code>ContactNameAlreadyExistsException</code>
 * is thrown when i found a contact with the same name created.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/09/2015.
 * @version 1.0
 */
public class LossProtectedContactNameAlreadyExistsException extends LossProtectedWalletException {

    public static final String DEFAULT_MESSAGE = "CONTACT NAME ALREADY EXISTS EXCEPTION";

    public LossProtectedContactNameAlreadyExistsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public LossProtectedContactNameAlreadyExistsException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public LossProtectedContactNameAlreadyExistsException(final String message) {
        this(message, null);
    }

    public LossProtectedContactNameAlreadyExistsException() {
        this(DEFAULT_MESSAGE);
    }
}
