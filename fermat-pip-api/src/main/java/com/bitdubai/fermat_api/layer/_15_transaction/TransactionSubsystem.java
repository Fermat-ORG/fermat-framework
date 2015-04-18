package com.bitdubai.fermat_api.layer._15_transaction;

import com.bitdubai.fermat_api.Plugin;

/**
 * Created by loui on 16/02/15.
 */
public interface TransactionSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
