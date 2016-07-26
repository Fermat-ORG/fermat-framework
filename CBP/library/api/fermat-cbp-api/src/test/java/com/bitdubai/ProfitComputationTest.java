package com.bitdubai;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

import org.junit.Test;

/**
 * The class <code>com.bitdubai.ProfitComputationTest</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/11/2015.
 */
public class ProfitComputationTest {

    enum CurrencyType {
        FIAT,
        CRYPTO
    }

    interface Currency extends FermatEnum {
        CurrencyType getType();
    }

    enum FiatCurrency implements Currency {

        ARGENTINIAN_PESO("ARS"),
        US_DOLLAR("USD"),;

        final String code;

        FiatCurrency(String code) {

            this.code = code;
        }

        @Override
        public CurrencyType getType() {
            return CurrencyType.FIAT;
        }

        @Override
        public String getCode() {
            return this.code;
        }
    }

    enum CryptoCurrency implements Currency {

        BITCOIN("BTC"),
        LITECOIN("LTC"),;

        final String code;

        CryptoCurrency(String code) {

            this.code = code;
        }

        @Override
        public CurrencyType getType() {
            return CurrencyType.CRYPTO;
        }

        @Override
        public String getCode() {
            return this.code;
        }
    }

    class ExchangeTransaction {

        Currency currencySold;
        Currency currencyReceived;
        long amount;
        long exchangeRate;

        boolean registered;
        long remains;
    }

    @Test
    public void Construction_Valid_NotNull() throws Exception {
        System.out.println("hola leon");
    }

}
