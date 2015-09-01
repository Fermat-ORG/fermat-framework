package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.ActorAddressBookCryptoModulePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRegistry;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.ActorAddressBookCryptoModulePluginRoot;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import java.util.UUID;


@RunWith(MockitoJUnitRunner.class)
public class GetActorAddressBookRegistryTest extends TestCase {

    @Mock
    ErrorManager errorManager;

    @Mock
    LogManager logManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    Database database;

    @Mock
    DatabaseTableFactory table;

    UUID pluginId;

    ActorAddressBookCryptoModulePluginRoot actorAddressBookCryptoModulePluginRoot;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        actorAddressBookCryptoModulePluginRoot = new ActorAddressBookCryptoModulePluginRoot();
        actorAddressBookCryptoModulePluginRoot.setId(pluginId);
        actorAddressBookCryptoModulePluginRoot.setErrorManager(errorManager);
        actorAddressBookCryptoModulePluginRoot.setLogManager(logManager);
        actorAddressBookCryptoModulePluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
    }

    @Test
    public void testGetActorAddressBookRegistryTeste_NotNul() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(database);
        ActorAddressBookRegistry actorAddressBookRegistry = actorAddressBookCryptoModulePluginRoot.getActorAddressBookRegistry();
        assertNotNull(actorAddressBookRegistry);
    }

    @Test(expected=CantGetActorAddressBookRegistryException.class)
    public void testGetActorAddressBookRegistryTest_CantOpenDatabaseException() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenThrow(new CantOpenDatabaseException());

        actorAddressBookCryptoModulePluginRoot.getActorAddressBookRegistry();
    }

    @Ignore
    public void testGetActorAddressBookRegistryTest_DatabaseNotFoundException() throws Exception {
         /*
         * TODO This test should pass but there is a wrong design decision that makes a cast of the Database interface into the DatabaseFactory; we should really look into that
         */
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenThrow(new DatabaseNotFoundException());
        when(pluginDatabaseSystem.createDatabase(pluginId, pluginId.toString())).thenReturn(database);
        when(((DatabaseFactory) database).newTableFactory(pluginId, "crypto_address_book")).thenReturn(table);

        ActorAddressBookRegistry actorAddressBookRegistry = actorAddressBookCryptoModulePluginRoot.getActorAddressBookRegistry();
        assertNotNull(actorAddressBookRegistry);
    }

    @Test(expected=CantGetActorAddressBookRegistryException.class)
    public void testGetActorAddressBookRegistryTest_DatabaseNotFoundException_CantCreateDatabaseException() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenThrow(new DatabaseNotFoundException());
        when(pluginDatabaseSystem.createDatabase(pluginId, pluginId.toString())).thenThrow(new CantCreateDatabaseException());

        actorAddressBookCryptoModulePluginRoot.getActorAddressBookRegistry();
    }
}
