package com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantRegisterDebitException;

/**
 * Created by Yordin Alayn on 30.09.15.
 */
public interface CryptoBrokerWalletTransaction {

    CryptoBrokerStockTransactionRecord debit(String publickeyWalle,
                                        String publickeyBroker,
                                        String publicKeyCustomer,
                                        BalanceType balanceType,
                                        CurrencyType currencyType,
                                        float amount,
                                        String memo
    ) throws CantRegisterDebitException;

    CryptoBrokerStockTransactionRecord credit(String publickeyWalle,
                                         String publickeyBroker,
                                         String publicKeyCustomer,
                                         BalanceType balanceType,
                                         CurrencyType currencyType,
                                         float amount,
                                         String memo
    ) throws CantRegisterCreditException;
}
