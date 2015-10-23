package com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantRegisterDebitException;

/**
 * Created by ciencias on 7/6/15.
 */
public interface BitcoinWalletBalance {

   /*
    * Get the balance of the wallet, the result represents the
    * amount of satoshis the user has.
   */

    public long getBalance() throws CantCalculateBalanceException;

    public void debit(BitcoinWalletTransactionRecord cryptoTransaction) throws CantRegisterDebitException;

    public void credit(BitcoinWalletTransactionRecord cryptoTransaction) throws CantRegisterCreditException;


}
