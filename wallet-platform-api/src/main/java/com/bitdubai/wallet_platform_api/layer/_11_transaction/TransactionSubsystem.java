package com.bitdubai.wallet_platform_api.layer._11_transaction;

import com.bitdubai.wallet_platform_api.Plugin;

/**
 * Created by loui on 16/02/15.
 */
public interface TransactionSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
