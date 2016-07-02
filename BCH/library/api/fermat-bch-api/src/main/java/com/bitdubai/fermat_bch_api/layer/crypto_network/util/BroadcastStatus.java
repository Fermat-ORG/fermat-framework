package com.bitdubai.fermat_bch_api.layer.crypto_network.util;

import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.Status;

/**
 * Created by rodrigo on 12/31/15.
 */
public class BroadcastStatus {
    private int retriesCount;
    private Status status;
    private Exception lastException;
    private int connectedPeers;

    public BroadcastStatus() {
    }

    public BroadcastStatus(int retriesCount, Status status) {
        this.retriesCount = retriesCount;
        this.status = status;
    }

    /**
     * Getters and setters
     */

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

    public Exception getLastException() {
        return lastException;
    }

    public void setLastException(Exception lastException) {
        this.lastException = lastException;
    }

    public int getConnectedPeers() {
        return connectedPeers;
    }

    public void setConnectedPeers(int connectedPeers) {
        this.connectedPeers = connectedPeers;
    }
}
