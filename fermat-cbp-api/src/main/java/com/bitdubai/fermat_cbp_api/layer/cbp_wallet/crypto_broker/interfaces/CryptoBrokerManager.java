package com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantCreateCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantLoadCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantRegisterCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantTransactionCryptoBrokerException;

import java.util.List;

/**
 * Created by Yordin Alayn on 30.09.15.
 */
public interface CryptoBrokerManager {

    List<CryptoBroker> getTransactionsBankMoney() throws CantTransactionCryptoBrokerException;

    CryptoBroker registerBankMoney(
         final String bankTransactionId
        ,final String publicKeyCustomer
        ,final String publicKeyBroker
        ,final String balanceType
        ,final String transactionType
        ,final float  amount
        ,final long runningBookBalance
        ,final long runningAvailableBalance
        ,final long timestamp
        ,final String getMemo
    ) throws CantRegisterCryptoBrokerException;

    CryptoBroker loadCashMoneyWallet(String walletPublicKey) throws CantLoadCryptoBrokerException;

    void createCashMoney (String walletPublicKey) throws CantCreateCryptoBrokerException;
}
