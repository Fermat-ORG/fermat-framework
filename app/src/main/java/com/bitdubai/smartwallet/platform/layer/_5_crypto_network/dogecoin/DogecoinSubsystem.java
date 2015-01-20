package com.bitdubai.smartwallet.platform.layer._5_crypto_network.dogecoin;

import com.bitdubai.smartwallet.platform.layer._5_crypto_network.CantStartSubsystemException;
import com.bitdubai.smartwallet.platform.layer._5_crypto_network.CryptoNetwork;
import com.bitdubai.smartwallet.platform.layer._5_crypto_network.CryptoNetworkSubsystem;

/**
 * Created by ciencias on 30.12.14.
 */
public class DogecoinSubsystem implements CryptoNetworkSubsystem {
    @Override
    public void start() throws CantStartSubsystemException {
        throw new CantStartSubsystemException();
    }

    @Override
    public CryptoNetwork getCryptoNetwork() {
        return null;
    }
}
