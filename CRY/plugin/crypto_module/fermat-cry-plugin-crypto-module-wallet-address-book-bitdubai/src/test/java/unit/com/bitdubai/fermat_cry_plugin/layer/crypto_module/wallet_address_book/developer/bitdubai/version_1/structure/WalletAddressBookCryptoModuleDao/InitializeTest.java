package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletAddressBookCryptoModuleDao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.exceptions.CantInitializeWalletAddressBookCryptoModuleException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletAddressBookCryptoModuleDao;
import com.googlecode.catchexception.CatchException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.*;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class InitializeTest extends TestCase {

    @Mock
    ErrorManager errorManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    WalletAddressBookCryptoModuleDao dao;

    UUID pluginId;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        dao = new WalletAddressBookCryptoModuleDao(errorManager, pluginDatabaseSystem, pluginId);
    }

    @Ignore
    public void testInitialize_NotNull() throws Exception {
        /*
         * TODO This test should pass but there is a wrong design decision that makes a cast of the Database interface into the DatabaseFactory; we should really look into that
         */

        CatchException.catchException(dao).initialize();
        assertThat(CatchException.caughtException()).isNull();
    }

    @Test(expected=CantInitializeWalletAddressBookCryptoModuleException.class)
    public void testInitialize_ErrorManagerNull_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        dao.setErrorManager(null);
        dao.initialize();
    }

    @Test(expected=CantInitializeWalletAddressBookCryptoModuleException.class)
    public void testInitialize_PluginDatabaseSystemNull_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        dao.setPluginDatabaseSystem(null);
        dao.initialize();
    }

    @Test(expected=CantInitializeWalletAddressBookCryptoModuleException.class)
    public void testInitialize_PluginIdNull_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        dao.setPluginId(null);
        dao.initialize();
    }

    @Test(expected=CantInitializeWalletAddressBookCryptoModuleException.class)
    public void testInitialize_CantCreateDatabaseException_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenThrow(new CantOpenDatabaseException());
        dao.initialize();
    }
}
