package com.bitdubai.fermat_api.layer._1_definition.transaction_transference_protocol;

import java.util.UUID;

/**
 * Created by eze on 09/06/15.
 */
public class Transaction <E> {
    private UUID transactionID;
    private E information;
    private String action;
    private long timestamp;

    // IMPORTANT: Fill the action parameter with the corresponding Action value applying name method.
    // Examples: Action.APPLY.name(), Action.REVERT.name();

    public Transaction(UUID transactionID, E information , String action, long timestamp) {
        this.transactionID = transactionID;
        this.information = information;
        this.action = action;
        this.timestamp = timestamp;
    }

    public UUID getTransactionID(){
        return this.transactionID;
    }

    public E getInformation() {
        return this.information;
    }

    public String getAction(){
        return this.action;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

}
