package com.bitdubai.fermat_core.layer.ccp_wallet_module.multi_account_wallet;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.ccp_wallet_module.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.ccp_wallet_module.WalletModuleSubsystem;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.multi_account_wallet.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 27/05/15.
 */
public class MultiAccountWalletWalletModuleSubsystem implements WalletModuleSubsystem {

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
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }

}
