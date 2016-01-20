package com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces;


public interface AmountToSellStep extends NegotiationStep {

    String getCurrencyToSell();

    String getCurrencyToReceive();

    String getAmountToSell();

    String getAmountToReceive();

    String getExchangeRateValue();
}
