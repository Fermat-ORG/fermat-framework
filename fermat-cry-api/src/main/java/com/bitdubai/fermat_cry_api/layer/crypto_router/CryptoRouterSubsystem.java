package com.bitdubai.fermat_cry_api.layer.crypto_router;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by eze on 2015.06.17..
 */
public interface CryptoRouterSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
