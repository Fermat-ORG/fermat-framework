package com.bitdubai.fermat_cbp_api.layer.cbp_business_transaction.crypto_money_stock_replenishment.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.business_transaction.BusinessTransaction;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CryptoCurrencyType;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 17.09.2015
 */

public interface CryptoMoneyStockReplenishment extends BusinessTransaction {

        CryptoCurrencyType getCryptoCurrencyType();

}
