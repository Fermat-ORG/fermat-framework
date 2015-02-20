package com.bitdubai.wallet_platform_core.layer._8_crypto;

import com.bitdubai.wallet_platform_api.Plugin;
import com.bitdubai.wallet_platform_api.layer.CantStartLayerException;
import com.bitdubai.wallet_platform_api.layer.PlatformLayer;
import com.bitdubai.wallet_platform_api.layer._8_crypto.CantStartSubsystemException;
import com.bitdubai.wallet_platform_api.layer._8_crypto.CryptoSubsystem;
import com.bitdubai.wallet_platform_core.layer._8_crypto.address_book.AddressBookSubsystem;

/**
 * Created by loui on 20/02/15.
 */
public class CryptoLayer implements PlatformLayer {
    
    private Plugin mAddressBook;
    
    public Plugin getmAddressBook(){
        return mAddressBook;
    }

    @Override
    public void start() throws CantStartLayerException {

        CryptoSubsystem addressBookSubsystem = new AddressBookSubsystem();

        try {
            addressBookSubsystem.start();
            mAddressBook = ((AddressBookSubsystem) addressBookSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }
        
    }
}
