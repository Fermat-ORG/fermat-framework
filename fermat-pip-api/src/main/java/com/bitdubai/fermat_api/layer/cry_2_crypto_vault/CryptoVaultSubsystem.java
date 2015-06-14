package com.bitdubai.fermat_api.layer.cry_2_crypto_vault;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by loui on 21/05/15.
 */
public interface CryptoVaultSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
