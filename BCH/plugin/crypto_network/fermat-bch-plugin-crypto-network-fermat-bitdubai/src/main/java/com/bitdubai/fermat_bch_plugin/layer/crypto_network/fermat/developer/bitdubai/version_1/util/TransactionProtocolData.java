package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Action;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptonetwork.fermat.developer.bitdubai.version_1.util.TransactionProtocolData</code>
 * holds the data used to transfer transaction information on the transmission protocol
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 09/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class TransactionProtocolData {
    private UUID transactionId;
    private CryptoTransaction cryptoTransaction;
    private Action action;
    private long timestamp;

    /**
     * default constructor
     */
    public TransactionProtocolData() {
    }

    /**
     * overloaded constructor
     * @param transactionId
     * @param cryptoTransaction
     * @param action
     * @param timestamp
     */
    public TransactionProtocolData(UUID transactionId, CryptoTransaction cryptoTransaction, Action action, long timestamp) {
        this.transactionId = transactionId;
        this.cryptoTransaction = cryptoTransaction;
        this.action = action;
        this.timestamp = timestamp;
    }

    /**
     * Getters and setters
     * @return
     */
    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public CryptoTransaction getCryptoTransaction() {
        return cryptoTransaction;
    }

    public void setCryptoTransaction(CryptoTransaction cryptoTransaction) {
        this.cryptoTransaction = cryptoTransaction;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
