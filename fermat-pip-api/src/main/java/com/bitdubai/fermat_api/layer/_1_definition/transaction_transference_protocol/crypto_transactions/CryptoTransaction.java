package com.bitdubai.fermat_api.layer._1_definition.transaction_transference_protocol.crypto_transactions;

import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;

/**
 * Created by eze on 11/06/15.
 */

public class CryptoTransaction{
    private String transactionHash;
    private String addressFrom;
    private String addressTo;
    private CryptoCurrency cryptoCurrency;
    private long cryptoAmount;
    private String cryptoStatus;


    public CryptoTransaction(String transactionHash,
                             String addressFrom,
                             String addressTo,
                             CryptoCurrency cryptoCurrency,
                             long cryptoAmount,
                             String cryptoStatus) {

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

    public String getAddressFrom() {
        return addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public long getCryptoAmount() {
        return cryptoAmount;
    }

    public String getCryptoStatus() {
        return cryptoStatus;
    }
}
