package com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_sale.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Yordin Alayn on 09.10.15.
 */
public class CantListSaleNegotianionsException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET LIST SALE NEGOTIATIONS EXCEPTION";

    public CantListSaleNegotianionsException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}