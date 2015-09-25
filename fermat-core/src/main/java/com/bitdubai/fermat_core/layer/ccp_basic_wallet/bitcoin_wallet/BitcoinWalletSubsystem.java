package com.bitdubai.fermat_core.layer.ccp_basic_wallet.bitcoin_wallet;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.ccp_basic_wallet.BasicWalletSubsystem;
import com.bitdubai.fermat_api.layer.ccp_basic_wallet.CantStartSubsystemException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 30/04/15.
 */
public class BitcoinWalletSubsystem implements BasicWalletSubsystem {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void start() throws CantStartSubsystemException {
        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            plugin = developerBitDubai.getPlugin();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }

}
