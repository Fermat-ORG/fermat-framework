package com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Angel on 07/12/2015.
 */

public class CantUpdateBankAccountPurchaseException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE BANK ACCOUNT SALE";

    public CantUpdateBankAccountPurchaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateBankAccountPurchaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantUpdateBankAccountPurchaseException(final String message) {
        this(message, null);
    }

    public CantUpdateBankAccountPurchaseException() {
        this(DEFAULT_MESSAGE);
    }
}
