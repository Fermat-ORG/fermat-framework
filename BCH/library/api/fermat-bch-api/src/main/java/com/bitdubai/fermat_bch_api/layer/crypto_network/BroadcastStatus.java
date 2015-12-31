package com.bitdubai.fermat_bch_api.layer.crypto_network;

import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.Status;

/**
 * Created by rodrigo on 12/31/15.
 */
public class BroadcastStatus {
    private int retriesCount;
    private Status status;

    public BroadcastStatus() {
    }

    public BroadcastStatus(int retriesCount, Status status) {
        this.retriesCount = retriesCount;
        this.status = status;
    }

    public int getRetriesCount() {
        return retriesCount;
    }

    public void setRetriesCount(int retriesCount) {
        this.retriesCount = retriesCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
