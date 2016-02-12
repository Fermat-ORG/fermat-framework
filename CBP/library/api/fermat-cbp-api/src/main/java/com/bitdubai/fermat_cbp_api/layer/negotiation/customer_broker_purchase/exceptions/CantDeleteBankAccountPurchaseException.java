package com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Angel on 07/12/2015.
 */

public class CantDeleteBankAccountPurchaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T DELETE BANK ACCOUNT SALE";

    public CantDeleteBankAccountPurchaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantDeleteBankAccountPurchaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantDeleteBankAccountPurchaseException(final String message) {
        this(message, null);
    }

    public CantDeleteBankAccountPurchaseException() {
        this(DEFAULT_MESSAGE);
    }
}
