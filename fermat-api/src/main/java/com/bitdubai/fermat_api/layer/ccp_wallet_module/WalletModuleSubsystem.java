package com.bitdubai.fermat_api.layer.ccp_wallet_module;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by loui on 21/05/15.
 */
public interface WalletModuleSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
