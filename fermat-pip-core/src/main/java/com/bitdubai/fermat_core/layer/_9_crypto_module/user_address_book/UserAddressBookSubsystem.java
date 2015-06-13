package com.bitdubai.fermat_core.layer._9_crypto_module.user_address_book;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer._9_crypto_module.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._9_crypto_module.CryptoSubsystem;
import com.bitdubai.fermat_cry_plugin.layer._9_crypto_module.user_address_book.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by loui on 20/02/15.
 */
public class UserAddressBookSubsystem implements CryptoSubsystem {

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
