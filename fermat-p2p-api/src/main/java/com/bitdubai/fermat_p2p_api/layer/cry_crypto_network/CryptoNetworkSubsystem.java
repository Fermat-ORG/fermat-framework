package com.bitdubai.fermat_p2p_api.layer.cry_crypto_network;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by ciencias on 30.12.14.
 */
public interface CryptoNetworkSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
