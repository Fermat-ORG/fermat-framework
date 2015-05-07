package com.bitdubai.fermat_core.layer._8_crypto;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer._8_crypto.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer._8_crypto.CryptoSubsystem;
import com.bitdubai.fermat_core.layer._8_crypto.user_address_book.UserAddressBookSubsystem;
import com.bitdubai.fermat_core.layer._8_crypto.wallet_address_book.WalletAddressBookSubsystem;

/**
 * Created by loui on 20/02/15.
 */
public class CryptoLayer implements PlatformLayer {
    
    private Plugin mUserAddressBook;
    private Plugin mWalletAddressBook;




    public Plugin getmWalletAddressBook() {
        return mWalletAddressBook;
    }
    public Plugin getmUserAddressBook(){
        return mUserAddressBook;
    }





    @Override
    public void start() throws CantStartLayerException {

        CryptoSubsystem userAddressBookSubsystem = new UserAddressBookSubsystem();

        try {
            userAddressBookSubsystem.start();
            mUserAddressBook = ((UserAddressBookSubsystem) userAddressBookSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }


        CryptoSubsystem walletAddressBookSubsystem = new WalletAddressBookSubsystem();

        try {
            walletAddressBookSubsystem.start();
            mWalletAddressBook = ((WalletAddressBookSubsystem) walletAddressBookSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

    }
}
