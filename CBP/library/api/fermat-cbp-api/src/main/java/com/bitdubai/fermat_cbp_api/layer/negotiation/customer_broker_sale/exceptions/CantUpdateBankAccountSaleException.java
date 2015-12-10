package com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Angel on 07/12/2015.
 */

public class CantUpdateBankAccountSaleException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T UPDATE BANK ACCOUNT SALE";

    public CantUpdateBankAccountSaleException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantUpdateBankAccountSaleException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantUpdateBankAccountSaleException(final String message) {
        this(message, null);
    }

    public CantUpdateBankAccountSaleException() {
        this(DEFAULT_MESSAGE);
    }
}
