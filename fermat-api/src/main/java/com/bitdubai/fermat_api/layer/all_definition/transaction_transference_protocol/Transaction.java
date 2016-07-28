package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol;

import java.util.UUID;

/**
 * Created by eze on 09/06/15.
 */
public class Transaction<E> {
    private UUID transactionID;
    private E information;
    private Action action;
    private long timestamp;


    public Transaction(UUID transactionID, E information, Action action, long timestamp) {
        this.transactionID = transactionID;
        this.information = information;
        this.action = action;
        this.timestamp = timestamp;
    }

    public UUID getTransactionID() {
        return this.transactionID;
    }

    public E getInformation() {
        return this.information;
    }

    public Action getAction() {
        return this.action;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

}
