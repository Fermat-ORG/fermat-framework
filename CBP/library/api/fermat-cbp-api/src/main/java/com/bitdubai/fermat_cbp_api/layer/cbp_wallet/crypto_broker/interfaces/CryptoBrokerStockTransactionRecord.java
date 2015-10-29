package com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockTransaction;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 02.10.15.
 */
public interface CryptoBrokerStockTransactionRecord extends StockTransaction {

    float getRunningBookBalance();

    float getRunningAvailableBalance();

}