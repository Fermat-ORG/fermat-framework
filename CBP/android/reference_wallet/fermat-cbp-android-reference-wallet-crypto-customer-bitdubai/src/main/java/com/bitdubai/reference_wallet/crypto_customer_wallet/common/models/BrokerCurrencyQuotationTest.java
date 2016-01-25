package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Created by Yordin Alayn on 25.01.16.
 */
public class BrokerCurrencyQuotationTest {

    private String currencyBuy;

    private String currencyPay;

    private String exchangeRate;

    public BrokerCurrencyQuotationTest(
        String currencyBuy,
        String currencyPay,
        String exchangeRate
    ){
        this.currencyBuy = currencyBuy;
        this.currencyPay = currencyPay;
        this.exchangeRate = exchangeRate;
    }

    String getCurrencyBuy(){ return this.currencyBuy; }

    String getCurrencyPay(){ return this.currencyPay; }

    String getExchangeRate(){ return  this.exchangeRate; }
}
