package com.bitdubai.wallet_platform_api.layer._7_crypto_network;

/**
 * Created by ciencias on 30.12.14.
 */
public interface CryptoNetworkSubsystem {
    public void start () throws CantStartSubsystemException;
    public CryptoNetworkService getCryptoNetwork();
}
