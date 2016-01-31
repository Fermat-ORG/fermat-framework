package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_api.layer.world.interfaces.CurrencyPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by nelson on 27/01/16.
 */
public class EarningsPairTestData implements EarningsPair {

    private Currency selectedEarningCurrency;
    private CurrencyPair currencyPair;
    private UUID id;

    public EarningsPairTestData(Currency currency, final Currency currencyFrom, final Currency currencyTo) {
        id = UUID.randomUUID();

        this.selectedEarningCurrency = currency;

        currencyPair = new CurrencyPair() {
            @Override
            public Currency getCurrencyFrom() {
                return currencyFrom;
            }

            @Override
            public Currency getCurrencyTo() {
                return currencyTo;
            }

            @Override
            public String getCode() {
                return currencyFrom.getCode() + "/" + currencyTo.getCode();
            }
        };
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    @Override
    public Currency getSelectedEarningCurrency() {
        return selectedEarningCurrency;
    }

    @Override
    public String walletPublicKey() {
        return "earningWalletPublicKey";
    }
}
