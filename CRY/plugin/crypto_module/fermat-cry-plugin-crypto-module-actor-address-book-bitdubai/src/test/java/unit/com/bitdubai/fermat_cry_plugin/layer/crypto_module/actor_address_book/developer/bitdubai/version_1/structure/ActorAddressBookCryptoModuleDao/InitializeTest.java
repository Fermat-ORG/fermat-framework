package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookCryptoModuleException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDao;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class InitializeTest extends TestCase {

    @Mock
    ErrorManager mockErrorManager;

    @Mock
    PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    Database mockDatabase;

    ActorAddressBookCryptoModuleDao testCryptoModuleDao;

    UUID testPluginId;

    @Before
    public void setUp() throws Exception {
        testPluginId = UUID.randomUUID();
        testCryptoModuleDao = new ActorAddressBookCryptoModuleDao(mockErrorManager, mockPluginDatabaseSystem, testPluginId);
    }

    // mockDatabase exists and can be opened
    @Test
    public void testInitialize_NotNull() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        testCryptoModuleDao.initialize();
    }

    // cant open mockDatabase
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_CantOpenDatabaseException() throws Exception {
        doThrow(new CantOpenDatabaseException()).when(mockPluginDatabaseSystem).openDatabase(any(UUID.class), anyString());

        testCryptoModuleDao.initialize();
    }

    // mockDatabase not found exception, then cant create mockDatabase.
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_DatabaseNotFoundException() throws Exception {
        doThrow(new DatabaseNotFoundException()).when(mockPluginDatabaseSystem).openDatabase(any(UUID.class), anyString());
        doThrow(new CantCreateDatabaseException()).when(mockPluginDatabaseSystem).createDatabase(any(UUID.class), anyString());

        testCryptoModuleDao.initialize();
    }

    /*
     * TODO This test should pass but there is a wrong design decision that makes a cast of the Database interface into the DatabaseFactory; we should really look into that
     */
    // mockDatabase not found exception, then create mockDatabase.
    @Ignore
    public void testInitialize_DatabaseNotFoundException_CreateDatabase() throws Exception {
        doThrow(new DatabaseNotFoundException()).when(mockPluginDatabaseSystem).openDatabase(any(UUID.class), anyString());
        doReturn(mockDatabase).when(mockPluginDatabaseSystem).createDatabase(any(UUID.class), anyString());

        testCryptoModuleDao.initialize();
    }

    // error manager null
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_ErrorManagerNull_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        testCryptoModuleDao.setErrorManager(null);
        testCryptoModuleDao.initialize();
    }

    // plugin mockDatabase system null
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_PluginDatabaseSystemNull_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        testCryptoModuleDao.setPluginDatabaseSystem(null);
        testCryptoModuleDao.initialize();
    }

    // plugin id null
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_PluginIdNull_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        testCryptoModuleDao.setPluginId(null);
        testCryptoModuleDao.initialize();
    }
}
