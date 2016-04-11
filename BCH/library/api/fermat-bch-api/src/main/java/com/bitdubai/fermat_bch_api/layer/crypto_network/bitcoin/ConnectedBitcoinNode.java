package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin;

import org.bitcoinj.core.Peer;

/**
 * Represents a bitcoin node that we are connecte to.
 */
public class ConnectedBitcoinNode {
    private String address;
    private String version;
    private long pingTime;

    /**
     * constructor
     * @param address
     * @param version
     * @param pingTime
     */
    public ConnectedBitcoinNode(String address, String version, long pingTime) {
        this.address = address;
        this.version = version;
        this.pingTime = pingTime;
    }

    @Override
    public String toString() {
        return "address='" + address + '\'' +
                ", version='" + version + '\'';
    }

    public String getAddress() {
        return address;
    }

    public String getVersion() {
        return version;
    }

    public long getPingTime() {
        return pingTime;
    }
}
