package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDao;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantRegisterActorAddressBookException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleDao;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class RegisterActorAddressBookTest extends TestCase {

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

    UUID actorId;

    Actors actorType;

    UUID actorIdTo;

    Actors actorTypeTo;


    CryptoAddress cryptoAddress;


    ActorAddressBookCryptoModuleDao dao;

    UUID pluginId;

    @Before
    public void setUp() throws Exception {
        actorId = UUID.randomUUID();
        actorType = Actors.EXTRA_USER;
        actorIdTo = UUID.randomUUID();
        actorTypeTo = Actors.INTRA_USER;
        cryptoAddress = new CryptoAddress("asdadas", CryptoCurrency.BITCOIN);
        pluginId = UUID.randomUUID();
        dao = new ActorAddressBookCryptoModuleDao(errorManager, pluginDatabaseSystem, pluginId);
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(database);
        dao.initialize();

    }

    @Test
    public void testRegister_NotNull() throws Exception {
        when(database.getTable(anyString())).thenReturn(databaseTable);
        when(databaseTable.getEmptyRecord()).thenReturn(databaseTableRecord);
        dao.registerActorAddressBook(actorId, actorType, actorIdTo, actorTypeTo, cryptoAddress);
    }

    @Test(expected=CantRegisterActorAddressBookException.class)
    public void testCreateDatabase_CantInsertRecordException_CantRegisterActorAddressBookException() throws Exception {
        when(database.getTable(anyString())).thenReturn(databaseTable);
        when(databaseTable.getEmptyRecord()).thenReturn(databaseTableRecord);
        doThrow(new CantInsertRecordException()).when(databaseTable).insertRecord(any(DatabaseTableRecord.class));

        dao.registerActorAddressBook(actorId, actorType, actorIdTo, actorTypeTo, cryptoAddress);
    }

    @Test(expected=CantRegisterActorAddressBookException.class)
    public void testRegister_actorIdNull_CantRegisterActorAddressBookException() throws Exception {
        dao.registerActorAddressBook(null, actorType, actorIdTo, actorTypeTo, cryptoAddress);
    }

    @Test(expected=CantRegisterActorAddressBookException.class)
    public void testRegister_actorTypeNull_CantRegisterActorAddressBookException() throws Exception {
        dao.registerActorAddressBook(actorId, null, actorIdTo, actorTypeTo, cryptoAddress);
    }

    @Test(expected=CantRegisterActorAddressBookException.class)
    public void testRegister_cryptoAddressNull_CantRegisterActorAddressBookException() throws Exception {
        dao.registerActorAddressBook(actorId, actorType, actorIdTo, actorTypeTo, null);
    }
}
