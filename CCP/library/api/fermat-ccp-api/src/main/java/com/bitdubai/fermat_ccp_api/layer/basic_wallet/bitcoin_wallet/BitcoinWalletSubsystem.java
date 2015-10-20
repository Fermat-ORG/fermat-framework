package com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.CantStartSubsystemException;

/**
 * Created by eze on 2015.06.17..
 */
public interface BitcoinWalletSubsystem {
    public void start () throws CantStartSubsystemException;
    public Plugin getPlugin();
}
