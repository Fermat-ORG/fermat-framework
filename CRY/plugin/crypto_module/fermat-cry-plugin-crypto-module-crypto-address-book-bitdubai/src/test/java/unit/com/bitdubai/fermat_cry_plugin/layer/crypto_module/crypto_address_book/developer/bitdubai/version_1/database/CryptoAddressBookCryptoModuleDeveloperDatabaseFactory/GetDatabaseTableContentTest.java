package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDeveloperDatabaseFactory;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressBookCryptoModuleDatabaseException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 07/09/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableContentTest extends TestCase {

    @Mock
    private Database mockDatabase;

    @Mock
    private DeveloperObjectFactory developerObjectFactory;
    @Mock
    private DeveloperDatabaseTable developerDatabaseTable;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private DatabaseTable mockTable;

    @Mock
    DatabaseTableRecord mockDatabaseTableRecord;
    @Mock
    List<DatabaseTableRecord> databaseTableRecordList;

    private CryptoAddressBookCryptoModuleDeveloperDatabaseFactory databaseFactory;

    @Before
    public void setUp() throws CantCreateDatabaseException, CantInitializeCryptoAddressBookCryptoModuleDatabaseException, CantOpenDatabaseException, DatabaseNotFoundException {
        UUID testOwnerId = UUID.randomUUID();
        databaseTableRecordList = Arrays.asList(mockDatabaseTableRecord, mockDatabaseTableRecord, mockDatabaseTableRecord);

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);
        when(mockTable.getRecords()).thenReturn(databaseTableRecordList);
         databaseFactory = new CryptoAddressBookCryptoModuleDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        databaseFactory.initializeDatabase();

    }
    @Test
    public void getDatabaseTableContentTest() throws CantOpenDatabaseException, DatabaseNotFoundException, CantInitializeCryptoAddressBookCryptoModuleDatabaseException {

        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = databaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        assertThat(developerDatabaseTableRecordList).isNotNull();


    }

}