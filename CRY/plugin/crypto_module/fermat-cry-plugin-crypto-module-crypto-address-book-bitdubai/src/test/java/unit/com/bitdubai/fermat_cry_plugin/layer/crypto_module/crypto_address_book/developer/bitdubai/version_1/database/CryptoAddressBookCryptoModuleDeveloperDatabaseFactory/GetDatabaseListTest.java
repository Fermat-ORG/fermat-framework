package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDeveloperDatabaseFactory;


import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.database.CryptoAddressBookCryptoModuleDeveloperDatabaseFactory;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.exceptions.CantInitializeCryptoAddressBookCryptoModuleDatabaseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 07/09/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseListTest
{

    @Mock
    private DeveloperObjectFactory developerObjectFactory;

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    private CryptoAddressBookCryptoModuleDeveloperDatabaseFactory databaseFactory;

    @Before
    public void setUp() throws CantCreateDatabaseException, CantInitializeCryptoAddressBookCryptoModuleDatabaseException, CantOpenDatabaseException, DatabaseNotFoundException {
        UUID testOwnerId = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        databaseFactory = new CryptoAddressBookCryptoModuleDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        databaseFactory.initializeDatabase();

    }

    @Test
    public void getDatabaseListTest() throws CantOpenDatabaseException, DatabaseNotFoundException, CantInitializeCryptoAddressBookCryptoModuleDatabaseException {

        assertThat(databaseFactory.getDatabaseList(developerObjectFactory)).isInstanceOf(List.class);
    }

}

