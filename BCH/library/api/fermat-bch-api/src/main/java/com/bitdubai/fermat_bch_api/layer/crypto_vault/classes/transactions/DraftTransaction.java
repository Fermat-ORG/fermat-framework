package com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionInput;

import java.util.Map;

/**
 * Created by rodrigo on 2/10/16.
 */
public class DraftTransaction {
    private Transaction bitcoinTransaction;

    /**
     * constructor
     * @param bitcoinTransaction the initial bitcoin transaction with some inputs and outputs and unsigned.
     */
    public DraftTransaction(Transaction bitcoinTransaction) {
        this.bitcoinTransaction = bitcoinTransaction;
    }

    /**
     * Gets the funds used on this transaction by getting each individual input fund.
     * @return
     */
    public long getValue(){
        long totalValue = 0;
        for (TransactionInput transactionInput : bitcoinTransaction.getInputs()){
            totalValue = totalValue + transactionInput.getValue().getValue();
        }

        return totalValue;
    }


    /**
     * returns the funds that are distributed for each CryptoAddress specified in the transaction
     * @return
     */
    public Map<CryptoAddress, Long> getFundsDistribution(){
        //implement.
        return null;
    }

    /**
     * Returns the fee that will be used on this transaction.
     * @return
     */
    public long getFee(){
        //implement
       return 0;
    }

    /**
     * getters
     */
    public String getTxHash() {
        return bitcoinTransaction.getHashAsString();
    }


}
