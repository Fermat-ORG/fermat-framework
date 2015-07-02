package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDao;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.ActorAddressBookNotFoundException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDao;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class GetAllActorAddressBookByActorIdTest extends TestCase {

    @Mock
    ErrorManager errorManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    Database database;

    @Mock
    DatabaseTable databaseTable;

    @Mock
    DatabaseTableRecord databaseTableRecord;

    ActorAddressBookCryptoModuleDao dao;

    UUID actorId;

    UUID pluginId;

    @Before
    public void setUp() throws Exception {
        actorId = UUID.randomUUID();
        pluginId = UUID.randomUUID();
        dao = new ActorAddressBookCryptoModuleDao(errorManager, pluginDatabaseSystem, pluginId);
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(database);
        dao.initialize();

    }

    @Test
    public void testGetAllActorAddressBookByActorId_NotNull() throws Exception {
        when(databaseTableRecord.getStringValue(anyString())).thenReturn("EUS");
        List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(databaseTableRecord);
        when(databaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(database.getTable(anyString())).thenReturn(databaseTable);

        assertNotNull(dao.getAllActorAddressBookByActorId(actorId));
    }

    @Test(expected=CantGetActorAddressBookException.class)
    public void testGetAllActorAddressBookByActorId_actorIdNull_CantGetActorAddressBookException() throws Exception {
        dao.getAllActorAddressBookByActorId(null);
    }

    @Test(expected=ActorAddressBookNotFoundException.class)
    public void testGetAllActorAddressBookByActorId_ActorAddressBookNotFoundException() throws Exception {
        when(database.getTable(anyString())).thenReturn(databaseTable);

        dao.getAllActorAddressBookByActorId(actorId);
    }

    @Test(expected=CantGetActorAddressBookException.class)
    public void testGetAllActorAddressBookByActorId_CantLoadTableToMemoryException_ActorAddressBookNotFoundException() throws Exception {
        doThrow(new CantLoadTableToMemoryException()).when(databaseTable).loadToMemory();
        when(database.getTable(anyString())).thenReturn(databaseTable);

        dao.getAllActorAddressBookByActorId(actorId);
    }
}
