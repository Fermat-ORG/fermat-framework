package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsDatabaseException;

import org.junit.Test;

/**
 * Created by Nerio on 18/07/15.
 */
public class ThrowExceptionsTest {
    @Test(expected = CantInitializeWalletContactsDatabaseException.class)
    public void throwCantCreateBlockStoreFileExceptionTest() throws CantInitializeWalletContactsDatabaseException {
        throw new CantInitializeWalletContactsDatabaseException();
    }
}