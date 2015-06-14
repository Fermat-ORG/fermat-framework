package com.bitdubai.fermat_api.layer._19_niche_wallet_type;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by loui on 21/05/15.
 */
public interface NicheWalletTypeSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
