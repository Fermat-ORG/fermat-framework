package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.CantInitializeWalletContactsDatabaseException;


import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsDatabaseException;


import org.junit.Test;

import static org.junit.Assert.assertNotNull;


/**
 * Created by Nerio on 19/07/15.
 */
public class ConstructionTest {

    CantInitializeWalletContactsDatabaseException walletContactsDatabase;

    @Test
    public void testInitialize_WalletContactsDatabase_notNull() throws CantInitializeWalletContactsDatabaseException {
        walletContactsDatabase = new CantInitializeWalletContactsDatabaseException();
        assertNotNull(walletContactsDatabase.getClass());
    }
}
