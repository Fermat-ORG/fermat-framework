package com.bitdubai.fermat_core.layer.cry_crypto_module.crypto_address_book;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_cry_api.layer.crypto_module.CantStartSubsystemException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.CryptoSubsystem;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.DeveloperBitDubaiOld;

/**
 * Crypto Address Book Sub System
 *
 * Created by Leon Acosta (laion.cj91@gmail.com) on 02/09/2015.
 */
public class CryptoAddressBookSubsystem implements CryptoSubsystem {

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
            DeveloperBitDubaiOld developerBitDubaiOld = new DeveloperBitDubaiOld();
            plugin = developerBitDubaiOld.getPlugin();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }

}
