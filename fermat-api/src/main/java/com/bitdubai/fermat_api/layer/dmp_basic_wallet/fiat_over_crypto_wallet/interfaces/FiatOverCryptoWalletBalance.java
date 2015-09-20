package com.bitdubai.fermat_api.layer.dmp_basic_wallet.fiat_over_crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common_exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common_exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common_exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_interfaces.FiatOverCryptoTransactionRecord;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_basic_wallet.fiat_over_crypto_wallet.interfaces.FiatOverCryptoWalletBalance</code>
 * provides the basic operations that can be applied to modify and consult a balance
 */
public interface FiatOverCryptoWalletBalance {

    /**
     * The method <code>getBalance</code> returns the balance of the wallet
     * @return a number representing the actual balance
     * @throws CantCalculateBalanceException
     */
    public long getBalance() throws CantCalculateBalanceException;

    /**
     * The method <code>credit</code> registers an incoming transaction in the balance.
     *
     * @param transactionRecord the data of the incoming transaction
     * @throws CantRegisterCreditException
     */
    public void credit(FiatOverCryptoTransactionRecord transactionRecord) throws CantRegisterCreditException;

    /**
     * The method <code>debit</code> registers an outgoing transaction in the balance.
     *
     * @param transactionRecord the data of the outgoing transaction
     * @throws CantRegisterDebitException
     */
    public void debit(FiatOverCryptoTransactionRecord transactionRecord) throws CantRegisterDebitException;
}
