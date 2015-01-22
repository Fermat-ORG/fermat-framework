package com.bitdubai.smartwallet.platform.layer._6_crypto_network;

/**
 * Created by ciencias on 30.12.14.
 */
public interface CryptoNetworkSubsystem {
    public void start () throws CantStartSubsystemException;
    public CryptoNetwork getCryptoNetwork();
}
