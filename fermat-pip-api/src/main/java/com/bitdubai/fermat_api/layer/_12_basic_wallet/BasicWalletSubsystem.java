package com.bitdubai.fermat_api.layer._12_basic_wallet;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by loui on 21/04/15.
 */
public interface BasicWalletSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
