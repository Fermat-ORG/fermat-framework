package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
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
    ErrorManager errorManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    Database database;

    ActorAddressBookCryptoModuleDao dao;

    UUID pluginId;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        dao = new ActorAddressBookCryptoModuleDao(errorManager, pluginDatabaseSystem, pluginId);
    }

    // database exists and can be opened
    @Test
    public void testInitialize_NotNull() throws Exception {
        doReturn(database).when(pluginDatabaseSystem).createDatabase(any(UUID.class), anyString());
        dao.initialize();
    }

    // cant open database
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_CantOpenDatabaseException() throws Exception {
        doThrow(new CantOpenDatabaseException()).when(pluginDatabaseSystem).openDatabase(any(UUID.class), anyString());

        dao.initialize();
    }

    // database not found exception, then cant create database.
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_DatabaseNotFoundException() throws Exception {
        doThrow(new DatabaseNotFoundException()).when(pluginDatabaseSystem).openDatabase(any(UUID.class), anyString());
        doThrow(new CantCreateDatabaseException()).when(pluginDatabaseSystem).createDatabase(any(UUID.class), anyString());

        dao.initialize();
    }

    /*
     * TODO This test should pass but there is a wrong design decision that makes a cast of the Database interface into the DatabaseFactory; we should really look into that
     */
    // database not found exception, then create database.
    @Ignore
    public void testInitialize_DatabaseNotFoundException_CreateDatabase() throws Exception {
        doThrow(new DatabaseNotFoundException()).when(pluginDatabaseSystem).openDatabase(any(UUID.class), anyString());
        doReturn(database).when(pluginDatabaseSystem).createDatabase(any(UUID.class), anyString());

        dao.initialize();
    }

    // error manager null
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_ErrorManagerNull_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        dao.setErrorManager(null);
        dao.initialize();
    }

    // plugin database system null
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_PluginDatabaseSystemNull_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        dao.setPluginDatabaseSystem(null);
        dao.initialize();
    }

    // plugin id null
    @Test(expected=CantInitializeActorAddressBookCryptoModuleException.class)
    public void testInitialize_PluginIdNull_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        dao.setPluginId(null);
        dao.initialize();
    }
}
