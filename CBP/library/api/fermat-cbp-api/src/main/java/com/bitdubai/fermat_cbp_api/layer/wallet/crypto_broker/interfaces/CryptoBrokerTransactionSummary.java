package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.TransactionType;

/**
 * Created by Yordin Alayn on 02.10.15
 */
public interface CryptoBrokerTransactionSummary {

    CurrencyType getCurrencyType();

    float getRunningBookBalance();

    float getRunningAvailableBalance();
}
