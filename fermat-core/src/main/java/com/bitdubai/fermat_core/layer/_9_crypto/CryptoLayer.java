package com.bitdubai.fermat_core.layer._9_crypto;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer._9_crypto.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._9_crypto.CryptoSubsystem;
import com.bitdubai.fermat_core.layer._9_crypto.address_book.AddressBookSubsystem;

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
