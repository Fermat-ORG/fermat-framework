package com.bitdubai.wallet_platform_api.layer._8_crypto;

import com.bitdubai.wallet_platform_api.Plugin;

/**
 * Created by loui on 20/02/15.
 */
public interface CryptoSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
