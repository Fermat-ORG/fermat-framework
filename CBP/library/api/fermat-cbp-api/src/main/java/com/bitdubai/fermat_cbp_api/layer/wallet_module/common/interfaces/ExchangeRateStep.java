package com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces;

/**
 * Created by nelson on 11/12/15.
 */
public interface ExchangeRateStep extends NegotiationStep {

    String getCurrencyToSell();

    String getCurrencyToReceive();

    String getSuggestedExchangeRate();

    String getExchangeRate();
}
