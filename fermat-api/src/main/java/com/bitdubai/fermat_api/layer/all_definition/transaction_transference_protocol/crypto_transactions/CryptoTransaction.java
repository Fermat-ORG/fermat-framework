package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

/**
 * Created by eze on 11/06/15.
 */

public class CryptoTransaction{
    private String transactionHash;
    private CryptoAddress addressFrom;
    private CryptoAddress addressTo;
    private CryptoCurrency cryptoCurrency;
    private long cryptoAmount;
    private CryptoStatus cryptoStatus;


    public CryptoTransaction(String transactionHash,
                             CryptoAddress addressFrom,
                             CryptoAddress addressTo,
                             CryptoCurrency cryptoCurrency,
                             long cryptoAmount,
                             CryptoStatus cryptoStatus) {

        this.transactionHash = transactionHash;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.cryptoCurrency = cryptoCurrency;
        this.cryptoAmount = cryptoAmount;
        this.cryptoStatus = cryptoStatus;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    public CryptoCurrency getCryptoCurrency() {return cryptoCurrency;}

    public long getCryptoAmount() {
        return cryptoAmount;
    }

    public CryptoStatus getCryptoStatus() {
        return cryptoStatus;
    }
}
