package com.bitdubai.fermat_core.layer.cry_crypto_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_cry_api.layer.crypto_module.CantStartSubsystemException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.CryptoSubsystem;
import com.bitdubai.fermat_core.layer.cry_crypto_module.crypto_address_book.CryptoAddressBookSubsystem;

/**
 * CryptoLayer
 *
 * Created by Leon Acosta (laion.cj91@gmail.com) on 02/09/2015.
 */
public class CryptoLayer implements PlatformLayer {

    private Plugin mCryptoAddressBook;

    public Plugin getCryptoAddressBook() {
        return mCryptoAddressBook;
    }

    @Override
    public void start() throws CantStartLayerException {

        CryptoSubsystem cryptoAddressBookSubsystem = new CryptoAddressBookSubsystem();

        try {
            cryptoAddressBookSubsystem.start();
            mCryptoAddressBook = cryptoAddressBookSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());
            throw new CantStartLayerException();
        }
    }
}
