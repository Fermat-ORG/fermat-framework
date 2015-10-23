package com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantRegisterDebitException;

/**
 * Created by Yordin Alayn on 30.09.15.
 */
public interface CryptoBrokerTransaction {

    CryptoBrokerTransactionRecord debit(BalanceType balanceType) throws CantRegisterDebitException;

    CryptoBrokerTransactionRecord credit(BalanceType balanceType)  throws CantRegisterCreditException;
}
