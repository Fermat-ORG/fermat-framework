package com.bitdubai.fermat_draft.layer._7_crypto_network.dogecoin;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer._7_crypto_network.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._7_crypto_network.CryptoNetworkSubsystem;

/**
 * Created by ciencias on 30.12.14.
 */
public class DogecoinSubsystem implements CryptoNetworkSubsystem {
    @Override
    public void start() throws CantStartSubsystemException {
        throw new CantStartSubsystemException();
    }

    @Override
    public Plugin getPlugin() {
        return null;
    }
}
