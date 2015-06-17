package com.bitdubai.fermat_cry_api.layer.crypto_module;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by loui on 20/02/15.
 */
public interface CryptoSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
