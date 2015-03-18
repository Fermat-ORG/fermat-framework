package com.bitdubai.fermat_core.layer._8_crypto.address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._2_os.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._2_os.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer._5_user.extra_user.User;
import com.bitdubai.fermat_api.layer._5_user.UserTypes;
import com.bitdubai.fermat_api.layer._8_crypto.address_book.CryptoAddressBook;

import java.util.UUID;

/**
 * Created by ciencias on 3/18/15.
 */

/**
 * This class manages the relationship between users and crypto addresses by storing them on a Database Table.
 */

public class AddressBook implements CryptoAddressBook , DealsWithPluginDatabaseSystem {
    @Override
    public User getUserByCryptoAddress(CryptoAddress cryptoAddress) {
        return null;
    }

    /**
     * CryptoAddressBook interface implementation.
     */
    @Override
    public void registerUserCryptoAddress(UserTypes userType, UUID userId, CryptoAddress cryptoAddress) {

    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        
    }
}
