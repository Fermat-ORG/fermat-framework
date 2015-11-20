package com.bitdubai.fermat_bch_api.layer.crypto_network;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by rodrigo on 9/30/15.
 */
public interface CryptoNetworkSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
