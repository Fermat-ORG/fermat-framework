package com.bitdubai.fermat_core.layer._19_niche_wallet_type.bank_notes_wallet;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer._19_niche_wallet_type.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._19_niche_wallet_type.NicheWalletTypeSubsystem;
import com.bitdubai.fermat_dmp_plugin.layer._19_niche_wallet_type.bank_notes_wallet.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 27/05/15.
 */
public class BankNotesWalletNicheWalletTypeSubsystem implements NicheWalletTypeSubsystem {

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
