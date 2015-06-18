package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions;

/**
 * Created by eze on 11/06/15.
 */
public enum CryptoStatus {
    IDENTIFIED("IDENTIFIED"),
    RECEIVED("RECEIVED"),
    CONFIRMED("CONFIRMED"),
    REVERSED("REVERSED");

    String status;

    CryptoStatus(String status) {
        this.status = status;
    }
}
