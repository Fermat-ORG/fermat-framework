package com.bitdubai.fermat_api.layer.cry_3_crypto_module;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by loui on 20/02/15.
 */
public interface CryptoSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
