package com.bitdubai.wallet_platform_core.layer._7_crypto_network.litecoin;

import com.bitdubai.wallet_platform_api.Service;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._7_crypto_network.CryptoNetworkSubsystem;

/**
 * Created by ciencias on 30.12.14.
 */
public class LitecoinSubsystem implements CryptoNetworkSubsystem {
    @Override
    public void start() throws CantStartSubsystemException {
        throw new CantStartSubsystemException();
    }

    @Override
    public Service getCryptoNetwork() {
        return null;
    }
}
