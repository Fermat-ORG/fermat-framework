package com.bitdubai.fermat_cbp_api.all_definition.negotiation;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.PaymentType;

import java.util.UUID;

/**
 * Created by angel on 07/12/15.
 */

public interface NegotiationPaymentCurrency {
    UUID getPaymentCurrencyId();

    Currency getPaymentCurrency();

    PaymentType getPaymentType();
}
