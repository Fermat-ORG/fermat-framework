package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockTransaction;

import java.math.BigDecimal;

/**
 * Created by Franklin  Marcano 03.12.2015
 */
public interface CryptoBrokerStockTransaction extends StockTransaction {
    //TODO: Documentar

    BigDecimal getRunningBookBalance();

    BigDecimal getRunningAvailableBalance();

    BigDecimal getPreviousBookBalance();

    BigDecimal getPreviousAvailableBalance();
}