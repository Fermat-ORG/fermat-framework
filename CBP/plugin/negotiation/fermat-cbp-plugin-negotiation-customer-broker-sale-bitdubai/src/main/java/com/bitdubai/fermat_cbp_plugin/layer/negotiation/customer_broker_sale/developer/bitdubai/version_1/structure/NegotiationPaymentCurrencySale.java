package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_sale.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.PaymentType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.NegotiationPaymentCurrency;

import java.util.UUID;

/**
 * Created by angel on 07/12/15.
 */
public class NegotiationPaymentCurrencySale implements NegotiationPaymentCurrency {

    private final UUID paymentCurrencyId;
    private final Currency currency;
    private final PaymentType type;

    public NegotiationPaymentCurrencySale(UUID paymentCurrencyId, Currency currency,  PaymentType type){
        this.paymentCurrencyId = paymentCurrencyId;
        this.currency = currency;
        this.type = type;
    }
    @Override
    public UUID getPaymentCurrencyId() {
        return this.paymentCurrencyId;
    }

    @Override
    public Currency getPaymentCurrency() {
        return this.currency;
    }

    @Override
    public PaymentType getPaymentType() {
        return this.type;
    }
}
