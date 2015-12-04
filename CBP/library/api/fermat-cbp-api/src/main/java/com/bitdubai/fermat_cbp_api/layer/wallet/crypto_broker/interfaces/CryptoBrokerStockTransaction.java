package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockTransaction;

/**
 * Created by Franklin  Marcano 03.12.2015
 */
public interface CryptoBrokerStockTransaction extends StockTransaction {
    //TODO: Documentar

    float getRunningBookBalance();

    float getRunningAvailableBalance();

    float getPreviousBookBalance();

    float getPreviousAvailableBalance();
}