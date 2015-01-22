package com.bitdubai.smartwallet.platform.layer._6_crypto_network.litecoin;

import com.bitdubai.smartwallet.platform.layer._6_crypto_network.CantStartSubsystemException;
import com.bitdubai.smartwallet.platform.layer._6_crypto_network.CryptoNetwork;
import com.bitdubai.smartwallet.platform.layer._6_crypto_network.CryptoNetworkSubsystem;

/**
 * Created by ciencias on 30.12.14.
 */
public class LitecoinSubsystem implements CryptoNetworkSubsystem {
    @Override
    public void start() throws CantStartSubsystemException {
        throw new CantStartSubsystemException();
    }

    @Override
    public CryptoNetwork getCryptoNetwork() {
        return null;
    }
}
